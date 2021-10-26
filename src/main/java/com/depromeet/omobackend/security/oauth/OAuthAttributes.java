package com.depromeet.omobackend.security.oauth;

import com.depromeet.omobackend.exception.InvalidOAuthException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private gString nameAttributeKey;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) throws InvalidOAuthException {
        if (registrationId.equals("kakao")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> newAttributes = new HashMap<>(attributes);
            newAttributes.put("email", response.get("email"));
            return new OAuthAttributes(newAttributes,
                    userNameAttributeName);
        }
        else if (registrationId.equals("naver")){
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            Map<String, Object> newAttributes = new HashMap<>(attributes);
            newAttributes.put("email", response.get("email"));
            return new OAuthAttributes(attributes,
                    userNameAttributeName);
        }
        else {
            throw new InvalidOAuthException();
        }
    }
}
