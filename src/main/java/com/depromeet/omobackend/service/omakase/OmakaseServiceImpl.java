package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultDto;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.dto.response.StampsDto;
import com.depromeet.omobackend.exception.OmakaseNotFoundException;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.recommendation.RecommendationRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OmakaseServiceImpl implements OmakaseService {

    private final OmakaseRepository omakaseRepository;
    private final StampRepository stampRepository;
    private final RecommendationRepository recommendationRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public OmakaseSearchResultResponse searchOmakase(String level, String keyword) {
        authenticationUtil.getUser();
        List<OmakaseSearchResultDto> nameSearch = omakaseRepository.findAllByLevelAndNameLikeOrderByRecommendationsDescName(Level.valueOf(level), "%"+keyword+"%").stream()
                .map(this::omakaseSearchResult).collect(Collectors.toList());
        List<OmakaseSearchResultDto> countySearch = omakaseRepository.findAllByLevelAndCountyLikeOrderByRecommendationsDescName(Level.valueOf(level),"%"+keyword+"%").stream()
                .map(this::omakaseSearchResult).collect(Collectors.toList());

        return OmakaseSearchResultResponse.builder()
                .nameSearch(nameSearch)
                .countySearch(countySearch)
                .totalElements(nameSearch.size() + countySearch.size())
                .build();
    }

    @Override
    public OmakaseDetailsResponse getOmakase(long id) {
        User user = authenticationUtil.getUser();
        Omakase omakase = omakaseRepository.findById(id)
                .orElseThrow(OmakaseNotFoundException::new);

        return OmakaseDetailsResponse.builder()
                .imageUrl(omakase.getPhotoUrl())
                .name(omakase.getName())
                .description(omakase.getDescription())
                .address(omakase.getAddress())
                .county(omakase.getCounty())
                .phoneNumber(omakase.getPhoneNumber())
                .priceInformation(omakase.getPriceInformation())
                .businessHours(omakase.getBusinessHours())
                .isRecommendation(recommendationRepository.findByUserAndOmakase(user, omakase).isPresent())
                .stamps(stampRepository.findAllByUserAndIsCertifiedTrueOrderByCreatedDate(user).stream()
                    .map(stamp -> new StampsDto(stamp.getId(), stamp.getCreatedDate())).collect(Collectors.toList())
                )
                .build();
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

}
