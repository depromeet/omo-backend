package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultDto;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.exception.AlreadyCertificatedOmakaseException;
import com.depromeet.omobackend.exception.OmakaseNotFoundException;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.recommendation.RecommendationRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OmakaseServiceImpl implements OmakaseService {

    private final OmakaseRepository omakaseRepository;
    private final RecommendationRepository recommendationRepository;
    private final StampRepository stampRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public OmakaseSearchResultResponse searchOmakases(Pageable page, String level, String keyword) {
        authenticationUtil.getUser();
        Page<Omakase> omakases;
        List<OmakaseSearchResultDto> omakaseList;

        keyword = "%"+keyword+"%";

        if (level != null) {
            omakases = omakaseRepository.findDistinctByLevelAndNameLikeAndCountyLike(Level.valueOf(level), keyword, page);
            omakaseList = omakases.getContent().stream()
                    .map(this::omakaseSearchResult).collect(Collectors.toList());
        }
        else {
            omakases = omakaseRepository.findDistinctByNameLikeAndCountyLike(keyword, page);
            omakaseList = omakases.getContent().stream()
                    .map(this::omakaseSearchResult).collect(Collectors.toList());
        }

        return OmakaseSearchResultResponse.builder()
                .omakases(omakaseList)
                .totalElements(omakases.getTotalElements())
                .totalPages(omakases.getTotalPages())
                .build();
    }

    @Override
    public OmakaseDetailsResponse getOmakaseDetail(long id) {
        User user = authenticationUtil.getUser();
        Omakase omakase = omakaseRepository.findById(id)
                .orElseThrow(OmakaseNotFoundException::new);

        return OmakaseDetailsResponse.builder()
                .imageUrl(omakase.getPhotoUrl())
                .name(omakase.getName())
                .description(omakase.getDescription())
                .level(omakase.getLevel().getName())
                .address(omakase.getAddress())
                .county(omakase.getCounty())
                .phoneNumber(omakase.getPhoneNumber())
                .priceInformation(omakase.getPriceInformation())
                .businessHours(omakase.getBusinessHours())
                .isRecommendation(recommendationRepository.findByUserAndOmakase(user, omakase).isPresent())
                .recommendationCount(omakase.getRecommendationCount())
                .isCertification(getCertification(user, omakase))
                .build();
    }

    @Override
    public void isCertificatedOmakase(long id) {
        User user = authenticationUtil.getUser();

        Omakase omakase = omakaseRepository.findById(id)
                .orElseThrow(OmakaseNotFoundException::new);

        stampRepository.findByUserAndOmakase(user, omakase)
                .ifPresent(u -> {
                    throw new AlreadyCertificatedOmakaseException();
                });
    }

    private OmakaseSearchResultDto omakaseSearchResult(Omakase omakase) {
        return OmakaseSearchResultDto.builder()
                .id(omakase.getId())
                .name(omakase.getName())
                .county(omakase.getCounty())
                .address(omakase.getAddress())
                .level(omakase.getLevel().getName())
                .imageUrl(omakase.getPhotoUrl())
                .build();
    }

    private Boolean getCertification(User user, Omakase omakase) {
        Stamp stamp = stampRepository.findByUserAndOmakase(user, omakase)
                .orElse(null);

        if (stamp == null) return null;
        else return stamp.getIsCertified();
    }

}
