package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingDto {
    private Long rank;
    private String nickname;
    private Long stampCount;
}
