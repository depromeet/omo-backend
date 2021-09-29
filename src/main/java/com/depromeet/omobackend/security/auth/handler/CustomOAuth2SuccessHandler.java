package com.depromeet.omobackend.security.auth.handler;

import com.depromeet.omobackend.util.CookieUtils;
import com.depromeet.omobackend.util.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    private static final Logger logger = LogManager.getLogger(CustomOAuth2SuccessHandler.class);

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CookieUtils cookieUtils;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug(authentication.getPrincipal());
        }
        String frontendAppEntryPage = env.getProperty("test-page.entry");
        String jwt = jwtUtils.generateToken((DefaultOAuth2User) authentication.getPrincipal());
        response.addCookie(cookieUtils.generateJwtHttpOnlyCookie(env.getProperty("jwt.token-name"), jwt, Integer.valueOf(env.getProperty("jwt.expire-time")).intValue()));
        response.addCookie(cookieUtils.generateNormalCookie(env.getProperty("jwt.token-name") + "-flag", "true", Integer.valueOf(env.getProperty("jwt.expire-time")).intValue()));

        if (response.isCommitted()) {
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, frontendAppEntryPage);

    }


}