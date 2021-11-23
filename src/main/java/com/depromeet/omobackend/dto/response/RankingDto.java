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
    private Long ranking;
    private String nickname;
    private Integer stampCount;
    private String profileUrl;
    private Integer power;
    private String email;
}
