package com.depromeet.omobackend.dto.request;

import com.depromeet.omobackend.domain.omakase.Holiday;
import com.depromeet.omobackend.domain.omakase.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class OmakaseRegistrationRequest {

    @NotBlank
    private final String address;

    @NotBlank
    private final String businessHours;

    @NotBlank
    private final String county;

    @NotBlank
    private final String description;

    @NotBlank
    private final Holiday holiday;

    @NotBlank
    private final Level level;

    @NotBlank
    private final String name;

    @NotBlank
    private final String phoneNumber;

    @NotBlank
    private final String priceInformation;

}
