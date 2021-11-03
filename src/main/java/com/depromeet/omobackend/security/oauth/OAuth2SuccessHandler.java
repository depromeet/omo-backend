package com.depromeet.omobackend.security.oauth;

import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Value("${jwt.exp.refresh}")
    private long refreshExp;

    @Value("${front.redirect.home.url}")
    private String redirectHomeUrl;

    @Value("${front.redirect.join.url}")
    private String redirectJoinUrl;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String)oAuth2User.getAttributes().get("email");
        String targetUrl = null;

//        PrintWriter writer = response.getWriter();
//        Gson gson = new Gson();
//        OAuthSuccessResponse oAuthSuccessResponse;
        if (userRepository.findByEmail(email).isPresent()) {
            String access = jwtTokenProvider.generateAccessToken(email);
            String refresh = jwtTokenProvider.generateRefreshToken(email);

//           oAuthSuccessResponse = OAuthSuccessResponse.builder()
//                    .status("logIn")
//                    .email(email)
//                    .access(access)
//                    .refresh(refresh)
//                    .build();

           refreshTokenRepository.save(new RefreshToken(email, refresh, refreshExp*60));

           StringBuilder sb = new StringBuilder();
           sb.append(redirectHomeUrl)
                   .append("?status=").append("logIn")
                   .append("?email=").append(email)
                   .append("?access=").append(access)
                   .append("?refresh=").append(refresh);
           targetUrl = sb.toString();
        }
        else {
//            oAuthSuccessResponse = OAuthSuccessResponse.builder()
//                    .status("signOn")
//                    .email(email)
//                    .build();

            StringBuilder sb = new StringBuilder();
            sb.append(redirectJoinUrl)
                    .append("?status=").append("signOn")
                    .append("?email=").append(email);
            targetUrl = sb.toString();
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
//        writer.write(gson.toJson(oAuthSuccessResponse));
//        writer.flush();
//        writer.close();
    }
}