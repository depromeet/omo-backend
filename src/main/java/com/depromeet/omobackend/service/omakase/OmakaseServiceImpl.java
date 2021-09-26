package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultDto;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OmakaseServiceImpl implements OmakaseService {

    private final OmakaseRepository omakaseRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public OmakaseSearchResultResponse searchOmakase(String keyword) {
        authenticationUtil.getUser();
        return OmakaseSearchResultResponse.builder()
                .nameSearch(
                        omakaseRepository.findAllByNameLikeOrderByName(keyword).stream()
                                .map(this::omakaseSearchResult).collect(Collectors.toList())
                )
                .countrySearch(
                        omakaseRepository.findAllByCountryLikeOrderByName(keyword).stream()
                                .map(this::omakaseSearchResult).collect(Collectors.toList())
                )
                .build();
    }

    private OmakaseSearchResultDto omakaseSearchResult(Omakase omakase) {
        return OmakaseSearchResultDto.builder()
                .id(omakase.getId())
                .name(omakase.getName())
                .country(omakase.getCountry())
                .description(omakase.getDescription())
                .imageUrl(omakase.getPhotoUrl())
                .build();
    }

}
