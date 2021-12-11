package com.depromeet.omobackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OmakaseRegistrationRequest {

    @NotBlank
    private String address;

    @NotBlank
    private String businessHours;

    @NotBlank
    private String county;

    @NotBlank
    private String description;

    private String holiday;

    @NotBlank
    private String level;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String priceInformation;

}
