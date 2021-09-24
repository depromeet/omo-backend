package com.depromeet.omobackend.security.auth.dto;

import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * 소셜 로그인 연동 이후에 가져온 사용자의 이메일, 이름, 닉네임, 프로필 사진 URL을 저장하는 DTO
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String nickname;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String nickname, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.picture = picture;
    }

    private static OAuthAttributes of(String registrationId,
                                      String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profileImage"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .profileImage(picture)
                .build();
    }
}
