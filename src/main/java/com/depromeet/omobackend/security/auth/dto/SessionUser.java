package com.depromeet.omobackend.security.auth.dto;

import com.depromeet.omobackend.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String nickName;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.nickName = user.getNickname();
        this.email = user.getEmail();
        this.picture = user.getProfileImage();
    }
}
