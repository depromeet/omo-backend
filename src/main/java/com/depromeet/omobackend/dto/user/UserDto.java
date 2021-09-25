package com.depromeet.omobackend.dto.user;

import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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
                   String profileImage, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.nickname = nickname;
        this.email = email;
        this.description = description;
        this.isActivated = isActivated;
        this.profileImage = profileImage;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public UserDto(User source) {
        BeanUtils.copyProperties(source, this);
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .description(description)
                .profileImage(profileImage)
                .build();
    }
}
