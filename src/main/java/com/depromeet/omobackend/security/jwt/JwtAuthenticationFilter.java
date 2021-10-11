package com.depromeet.omobackend.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.depromeet.omobackend.util.CookieUtils;
import com.depromeet.omobackend.util.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

    @Resource
    private Environment env;

    @Autowired
    private CookieUtils cookieUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (requestMatcher.matches(request)) {

            String jwt = cookieUtils.getCookieValue(request, env.getProperty("jwt.token-name"));

            if (logger.isDebugEnabled()) {
                logger.debug(jwt);
            }

            if (jwt.isEmpty()) {

                if (logger.isDebugEnabled()) {
                    logger.debug("JWT is empty");
                }

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            } else {

                // JWT가 있다면 유효한지 검사
                if (!jwtUtils.isValidateToken(jwt)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("JWT is invalid");
                    }
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }

                if (jwtUtils.isTokenExpired(jwt)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("JWT is expired");
                    }
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }

                Map<String, Object> attributes = jwtUtils.getBobyFromToken(jwt);

                if (logger.isDebugEnabled()) {
                    logger.debug("JWT::" + attributes);
                }

                String userNameAttributeName = "sub";
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
                OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, authorities, userNameAttributeName);

                authentication.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);
    }

}
