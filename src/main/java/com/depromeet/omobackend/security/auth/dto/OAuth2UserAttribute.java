package com.depromeet.omobackend.security.auth.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2UserAttribute {
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String USER_EMAIL = "userEmail";

	public Map<String, Object> getOAuth2UserAttributes(String registrationId, String response) 
			throws JsonMappingException, JsonProcessingException {
		
		Map<String, Object> attributes = new HashMap<>();

		JsonNode node = new ObjectMapper().readTree(response);

		attributes.put(USER_ID, node.get("response").get("id").toString().replaceAll("\"", ""));
		attributes.put(USER_NAME, node.get("response").get("nickname"));
		attributes.put(USER_EMAIL, node.get("response").get("email"));

		return attributes;		
	}
	

}
