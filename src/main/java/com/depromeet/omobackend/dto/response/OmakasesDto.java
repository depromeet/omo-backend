package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmakasesDto {

    private Long id;

    private String name;

    private String photoUrl;

    private String country;

    private LocalDateTime createDate;

    private Boolean isCertified;

}
