package com.depromeet.omobackend.security.auth.dto;

import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttribute {
    private Map<String, Object> attributes;
    private String emailAttributeKey;
    private String email;

    @Builder
    public OAuthAttribute(Map<String, Object> attributes, String emailAttributeKey, String email) {
        this.attributes = attributes;
        this.emailAttributeKey = emailAttributeKey;
        this.email = email;
    }

    public static OAuthAttribute of(String registrationId, String userAttributeKey, Map<String, Object> attributes) {
        // TODO: 여기에 카카오, 네이버, 애플 구현 필요
        if ("naver".equals(registrationId)) {
            return ofNaver(userAttributeKey, attributes);
        }
        return ofNaver(userAttributeKey, attributes);
    }

    private static OAuthAttribute ofNaver(String userAttributeKey, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttribute.builder()
                .email((String) response.get("email"))
                .attributes(response)
                .emailAttributeKey(userAttributeKey)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }
}
