package com.depromeet.omobackend.security.auth;

import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.security.auth.dto.OAuth2UserAttribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * OAuth2 로그인 이후 가져온 사용자 정보를 기반으로 회원 가입 기능 구현
 */
public class OmoOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    public static final String BEARER = "Bearer ";
    private static final Logger logger = LogManager.getLogger(OmoOAuth2Service.class);
    private static final String MISSING_USER_INFO_ERROR_CODE = "missing_redirect_uri_access_code";
    public static final String AUTHORIZATION = "Authorization";
    public static final String EMAIL = "userEmail";

    @Autowired
    private OAuth2UserAttribute oauth2UserAttribute;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        String registrationId = getRegistrationId(userRequest);
        String resourceServerUri = getResourceServerUri(userRequest);
        String accessToken = getAccessToken(userRequest);

        String userEmailAttribute = OAuth2UserAttribute.USER_EMAIL;
        Map<String, Object> attributes = null;

        if (isNotNullResourceServerUri(resourceServerUri) && isNotNullAccessToken(accessToken)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set(AUTHORIZATION, BEARER + accessToken);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

            try {
                // 리소스 서버에게 사용자 정보 요청
                String response = restTemplate.postForObject(resourceServerUri, request, String.class);
                attributes = oauth2UserAttribute.getOAuth2UserAttributes(registrationId, response);
            } catch (OAuth2AuthorizationException ex) {
                OAuth2Error oauth2Error = ex.getError();
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_INFO_ERROR_CODE,
                    "Missing required redirect uri or access token for Client Registration: "
                            + getRegistrationId(userRequest),
                    null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }
        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(Role.USER.getKey())), attributes, userEmailAttribute);

    }

    private boolean isNotNullAccessToken(String accessToken) {
        return accessToken != null && !"".equals(accessToken);
    }

    private boolean isNotNullResourceServerUri(String resourceServerUri) {
        return resourceServerUri != null && !"".equals(resourceServerUri);
    }

    private String getAccessToken(OAuth2UserRequest userRequest) {
        return userRequest.getAccessToken().getTokenValue();
    }

    private String getResourceServerUri(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
    }

    private String getRegistrationId(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getRegistrationId();
    }
}
