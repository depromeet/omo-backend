package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmakaseSearchResultDto {

    private Long id;

    private String name;

    private String county;

    private String imageUrl;

    private String level;

    private String description;

}
