package com.depromeet.omobackend.security.oauth;

import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        String refresh = jwtTokenProvider.generateRefreshToken(email);
        String access = jwtTokenProvider.generateAccessToken(email);

        PrintWriter writer = response.getWriter();
        String token = "{ \"refresh\" : " + refresh + ", \"access\" : " + access + "}";
        writer.write(token);
        writer.flush();

//        refreshTokenRepository.save(new RefreshToken(email, refresh));
    }
}