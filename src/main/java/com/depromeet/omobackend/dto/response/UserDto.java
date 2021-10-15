package com.depromeet.omobackend.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String nickname;

    private String profileUrl;

    private Integer stampCount;

    private Integer power;

}
