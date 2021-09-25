package com.depromeet.omobackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyNicknameRequest {

    @Size(min = 2, max = 8)
    @NotBlank
    private String nickname;

}
