package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmakaseDetailsResponse {

    private String imageUrl;

    private String name;

    private String description;

    private String address;

    private String county;

    private String level;

    private String phoneNumber;

    private String openTime;

    private String closeTime;

    private List<StampsDto> stamps;

}
