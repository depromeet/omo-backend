package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmakaseDetailsResponse {

    private Boolean isRecommendation;

    private Boolean isCertification;

    private String imageUrl;

    private String name;

    private String description;

    private String address;

    private String county;

    private String phoneNumber;

    private String priceInformation;

    private String businessHours;

    private Long recommendationCount;

}
