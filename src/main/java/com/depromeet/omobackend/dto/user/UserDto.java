package com.depromeet.omobackend.dto.user;

import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserDto {
    private String nickname;
    private String email;
    private String description;
    private Boolean isActivated;
    private String profileImage;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public UserDto(String nickname, String email, String description, boolean isActivated,
                   String profileImage) {
        this.nickname = nickname;
        this.email = email;
        this.description = description;
        this.isActivated = isActivated;
        this.profileImage = profileImage;
        this.isActivated = true;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .profileUrl(profileImage)
                .build();
    }
}
