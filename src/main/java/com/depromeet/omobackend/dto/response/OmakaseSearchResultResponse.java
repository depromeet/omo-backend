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
public class OmakaseSearchResultResponse {

    private List<OmakaseSearchResultDto> nameSearch;

    private List<OmakaseSearchResultDto> countySearch;

}
