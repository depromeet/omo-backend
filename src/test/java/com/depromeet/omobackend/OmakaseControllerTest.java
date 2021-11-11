package com.depromeet.omobackend;

import com.depromeet.omobackend.domain.omakase.Category;
import com.depromeet.omobackend.domain.omakase.Holiday;
import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.repository.location.LocationRepository;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.recommendation.RecommendationRepository;
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
public class OmakaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OmakaseRepository omakaseRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    private MockMvc mvc;

    Omakase omakase;
    User user;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        user = userRepository.save(
                User.builder()
                        .nickname("테스트")
                        .email("test@gmail.com")
                        .profileUrl("asdf")
                        .role(Role.USER)
                        .build()
        );

        omakase = createOmakase("맛있는 오마카세", "수지구", "HIGH");
        createOmakase("맛있는 오마카세", "기흥구", "HIGH");
        createOmakase("맛있는 초밥", "수지구", "ENTRY");
        createOmakase("맛업는 초밥", "기흥구", "HIGH");
        createOmakase("맛있는 초밥", "수지구", "MIDDLE");
        createOmakase("맛있는 오마카세", "수지구", "MIDDLE");
        createOmakase("처인구 초밥", "수지구", "ENTRY");
        createOmakase("처인구 초밥", "처인구", "ENTRY");
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void searchOmakase() throws Exception {
        mvc.perform(get("/omakases")
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void searchOmakase2() throws Exception {
        mvc.perform(get("/omakases")
                .param("level","HIGH")
                .param("keyword","수지구")
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void searchOmakase3() throws Exception {
        mvc.perform(get("/omakases")
                .param("level","ENTRY")
                .param("keyword","처인구")
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void getOmakase() throws Exception {
        recommendationRepository.save(new Recommendation(user, omakase));
        omakase.plusRecommendationCount();

        mvc.perform(get("/omakase/" + omakase.getId())
        ).andExpect(status().isOk()).andDo(print());
    }

    private Omakase createOmakase(String name, String county, String level) {
        Omakase omakase = omakaseRepository.save(
                Omakase.builder()
                        .name(name)
                        .address("경기도 용인시 수지구 죽전동 몰라몰라 상가")
                        .county(county)
                        .phoneNumber("03211110000")
                        .description("12년 전통 오마카세")
                        .businessHours("점심: 11:00~13:30 / 저녁: 18:00~20:30")
                        .priceInformation("점심: 98,000 / 저녁: 130,000")
                        .category(Category.SUSHI)
                        .holiday(Holiday.MONDAY)
                        .level(Level.valueOf(level))
                        .build()
        );
        locationRepository.save(omakase.getLocation());
        return omakase;
    }




}
