package com.depromeet.omobackend.dto.request;

import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;

    @Size(min = 2, max = 8)
    private String nickname;

    private String profileUrl;

    @Builder
    public UserSaveRequestDto(String email, String nickname, String profileUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .role(Role.USER)
                .build();
    }
}
