package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.OmoBackendApplication;
import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OmoBackendApplication.class)
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        String email = userRepository.save(
                User.builder()
                        .nickname("테스트")
                        .email("test@gmail.com")
                        .profileUrl("asdf")
                        .role(Role.USER)
                        .build()
        ).getEmail();

        refreshTokenRepository.save(
                new RefreshToken(email, jwtTokenProvider.generateRefreshToken(email), 1256L)
        );

    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
        refreshTokenRepository.deleteAll();
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void logout() throws Exception {
        mvc.perform(delete("/logout"))
                .andExpect(status().isOk());

    }

}
