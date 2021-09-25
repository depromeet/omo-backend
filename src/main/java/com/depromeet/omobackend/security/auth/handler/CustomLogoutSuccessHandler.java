package com.depromeet.omobackend.security.auth.handler;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.depromeet.omobackend.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private Environment env;

    @Autowired
    private CookieUtils cookieUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String frontendAppEntryPage = env.getProperty("frontend-app.entry");
        response.addCookie(cookieUtils.generateRemoveJwtCookie(env.getProperty("jwt.token-name"), ""));
        response.addCookie(cookieUtils.generateRemoveJwtCookie(env.getProperty("jwt.token-name") + "-flag", ""));
        getRedirectStrategy().sendRedirect(request, response, frontendAppEntryPage);

    }

}
