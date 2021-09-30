package com.depromeet.omobackend.security.config;

import com.depromeet.omobackend.security.auth.OmoOAuth2Service;
import com.depromeet.omobackend.security.auth.handler.CustomLogoutSuccessHandler;
import com.depromeet.omobackend.security.auth.handler.CustomOAuth2SuccessHandler;
import com.depromeet.omobackend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String JSESSIONID = "JSESSIONID";
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/oauth2Login", "/api/**", "/static/**", "/v2/api-docs",
                                "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .loginPage("/oauth2Login")
                    .redirectionEndpoint()
                    .baseUri("/oauth2/code/*") // callback URL: http://localhost:8080/oauth2/code/naver
                .and()
                    .userInfoEndpoint()
                    .userService(omoOAuth2UserService())
                .and()
                    .successHandler(customOAuth2SuccessHandler)
                .and()
                    .logout()
                    .deleteCookies(JSESSIONID)
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> omoOAuth2UserService() {
        return new OmoOAuth2Service();
    }
}
