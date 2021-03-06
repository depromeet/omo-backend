package com.depromeet.omobackend.security.config;

import com.depromeet.omobackend.exception.handler.AuthenticationEntryPointImpl;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import com.depromeet.omobackend.security.jwt.TokenFilter;
import com.depromeet.omobackend.security.oauth.OAuth2SuccessHandler;
import com.depromeet.omobackend.security.oauth.OmoOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final OmoOAuthService omoOauthService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint) //????????? ??????????????? ??????
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "test").permitAll()
                    .antMatchers(HttpMethod.GET, "/user/check/**").permitAll()
                    .antMatchers("/user").permitAll()
                    .antMatchers("/profile").permitAll()
                    .antMatchers("/login/oauth2/code/naver").permitAll()
                    .antMatchers("/login/oauth2/code/kakao").permitAll()
                    .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                    .antMatchers("/workspace/omo-backend/images/profile/**").permitAll()
                    .antMatchers("/workspace/omo-backend/images/omakases/**").permitAll()
                    .antMatchers(HttpMethod.PATCH, "/auth").permitAll()
                    .antMatchers(HttpMethod.POST, "/registration/omakase").permitAll()
                    .antMatchers(HttpMethod.PATCH, "/registration/omakase/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(omoOauthService)
                    .and()
                    .successHandler(oAuth2SuccessHandler)
                    .permitAll()
        ;
        http.addFilterBefore(new TokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/workspace/omo-backend/images/profile/**")
                .addResourceLocations("file:///workspace/omo-backend/images/profile/");
        registry.addResourceHandler("/workspace/omo-backend/images/omakases/**")
                .addResourceLocations("file:///workspace/omo-backend/images/omakases/");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
