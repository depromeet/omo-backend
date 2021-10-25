package com.depromeet.omobackend.security.oauth;

import com.depromeet.omobackend.exception.InvalidOAuthException;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) throws InvalidOAuthException {
        if (registrationId.equals("kakao")) {
            return new OAuthAttributes(attributes,
                    userNameAttributeName,
                    (String) attributes.get("id"));
        }
        else if (registrationId.equals("naver")){
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return new OAuthAttributes(attributes,
                    userNameAttributeName,
                    (String) attributes.get("id"));
        }
        else {
            throw new InvalidOAuthException();
        }
    }
}
