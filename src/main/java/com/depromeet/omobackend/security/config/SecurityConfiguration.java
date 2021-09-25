package com.depromeet.omobackend.security.config;

import com.depromeet.omobackend.security.auth.CustomOAuth2UserService;
import com.depromeet.omobackend.security.auth.handler.CustomOAuth2SuccessHandler;
import com.depromeet.omobackend.security.auth.handler.CustomLogoutSuccessHandler;
import com.depromeet.omobackend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/public/"};

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/", "/oauth2Login", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                        .antMatchers("/index.html").permitAll()
                        .antMatchers("/static/**").permitAll()
                        .antMatchers("/main.do").permitAll()
                        .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .loginPage("/oauth2Login")
                    .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                .and()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService())
                .and()
                    .successHandler(customOAuth2SuccessHandler)
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(customLogoutSuccessHandler)

                .and()
                .addFilterBefore(jwtAuthenticationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

}
