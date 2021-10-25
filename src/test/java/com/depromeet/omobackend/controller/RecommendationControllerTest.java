package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.OmoBackendApplication;
import com.depromeet.omobackend.domain.omakase.Category;
import com.depromeet.omobackend.domain.omakase.Holiday;
import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.repository.location.LocationRepository;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OmoBackendApplication.class)
@ActiveProfiles("test")
public class RecommendationControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OmakaseRepository omakaseRepository;

    @Autowired
    private LocationRepository locationRepository;

    private MockMvc mvc;

    Omakase omakase;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        userRepository.save(
                User.builder()
                        .nickname("테스트")
                        .email("test@gmail.com")
                        .profileUrl("asdf")
                        .role(Role.USER)
                        .build()
        );

        omakase = omakaseRepository.save(
                Omakase.builder()
                        .name("맛있는오마카세")
                        .address("경기도 용인시 수지구 죽전동 몰라몰라 상가")
                        .county("수지구")
                        .phoneNumber("03211110000")
                        .photoUrl("asdf")
                        .description("12년 전통 오마카세")
                        .businessHours("점심: 11:00~13:30 / 저녁: 18:00~20:30")
                        .priceInformation("점심: 98,000 / 저녁: 130,000")
                        .category(Category.SUSHI)
                        .holiday(Holiday.MONDAY)
                        .level(Level.HIGH)
                        .build()
        );

        locationRepository.save(omakase.getLocation());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void recommendation() throws Exception {
        mvc.perform(patch("/recommendation/" + omakase.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        mvc.perform(patch("/recommendation/" + omakase.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
