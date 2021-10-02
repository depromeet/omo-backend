package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultDto;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.dto.response.StampsDto;
import com.depromeet.omobackend.exception.OmakaseNotFoundException;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OmakaseServiceImpl implements OmakaseService {

    private final OmakaseRepository omakaseRepository;
    private final StampRepository stampRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public OmakaseSearchResultResponse searchOmakase(String keyword) {
        authenticationUtil.getUser();
        return OmakaseSearchResultResponse.builder()
                .nameSearch(
                        omakaseRepository.findAllByNameLikeOrderByName("%"+keyword+"%").stream()
                                .map(this::omakaseSearchResult).collect(Collectors.toList())
                )
                .countrySearch(
                        omakaseRepository.findAllByCountryLikeOrderByName("%"+keyword+"%").stream()
                                .map(this::omakaseSearchResult).collect(Collectors.toList())
                )
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
                .level(omakase.getLevel().getName())
                .country(omakase.getCountry())
                .phoneNumber(omakase.getPhoneNumber())
                .openTime(omakase.getOpenTime().toString())
                .closeTime(omakase.getCloseTime().toString())
                .stamps(stampRepository.findAllByUserAndCertifiedTrueOrderByCreatedDate(user).stream()
                    .map(stamp -> new StampsDto(stamp.getId(), stamp.getCreatedDate())).collect(Collectors.toList())
                )
                .build();
    }

    private OmakaseSearchResultDto omakaseSearchResult(Omakase omakase) {
        return OmakaseSearchResultDto.builder()
                .id(omakase.getId())
                .name(omakase.getName())
                .country(omakase.getCountry())
                .level(omakase.getLevel().getName())
                .description(omakase.getDescription())
                .imageUrl(omakase.getPhotoUrl())
                .build();
    }

}
