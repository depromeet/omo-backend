package com.depromeet.omobackend.security.oauth;

import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import com.depromeet.omobackend.dto.response.OAuthSuccessResponse;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${jwt.exp.refresh}")
    private Long refreshExp;

    JwtTokenProvider jwtTokenProvider;
    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        OAuthSuccessResponse oAuthSuccessResponse;
        if (userRepository.findByEmail(email).isPresent()){
            String access = jwtTokenProvider.generateAccessToken(email);
            String refresh = jwtTokenProvider.generateRefreshToken(email);

           oAuthSuccessResponse = OAuthSuccessResponse.builder()
                    .status("logIn")
                    .email(email)
                    .access(access)
                    .refresh(refresh)
                    .build();

           refreshTokenRepository.save(new RefreshToken(email, refresh, refreshExp*60));
        }
        else {
            oAuthSuccessResponse = OAuthSuccessResponse.builder()
                    .status("signOn")
                    .email(email)
                    .build();
        }
        writer.write(gson.toJson(oAuthSuccessResponse));
        writer.flush();
        writer.close();
    }
}