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
public class ProfileResponse {

    private UserDto user;

    private Long omakaseCount;

    private List<OmakaseDto> omakases;

}
