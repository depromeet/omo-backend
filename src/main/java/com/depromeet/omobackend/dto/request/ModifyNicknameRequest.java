package com.depromeet.omobackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNicknameRequest {

    @Max(20)
    @NotBlank
    private String nickname;

}
