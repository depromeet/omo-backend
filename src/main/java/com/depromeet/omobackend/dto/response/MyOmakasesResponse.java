package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyOmakasesResponse {

    private List<OmakasesDto> omakases;

}
