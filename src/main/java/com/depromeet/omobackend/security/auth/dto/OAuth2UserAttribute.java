package com.depromeet.omobackend.security.auth.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2의 인증 공급자로부터 취득한 사용자 정보를 다시 애플리케이션의 목적에 맞도록 구성
 */
@Component
public class OAuth2UserAttribute {
	public static final String USER_EMAIL = "userEmail";

	public Map<String, Object> getOAuth2UserAttributes(String registrationId, String response) 
			throws JsonProcessingException {

		Map<String, Object> attributes = new HashMap<>();

		JsonNode node = new ObjectMapper().readTree(response);
		attributes.put(USER_EMAIL, node.get("response").get("email"));

		return attributes;
	}
}
