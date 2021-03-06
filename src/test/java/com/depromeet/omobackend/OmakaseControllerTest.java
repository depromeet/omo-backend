package com.depromeet.omobackend;

import com.depromeet.omobackend.domain.omakase.Category;
import com.depromeet.omobackend.domain.omakase.Holiday;
import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.domain.user.User;
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

    /*@Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OmakaseRepository omakaseRepository;

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
                        .nickname("?????????")
                        .email("test@gmail.com")
                        .profileUrl("asdf")
                        .role(Role.USER)
                        .build()
        );

        omakase = createOmakase("????????? ????????????", "?????????", "HIGH");
        createOmakase("????????? ????????????", "?????????", "HIGH");
        createOmakase("????????? ??????", "?????????", "ENTRY");
        createOmakase("????????? ??????", "?????????", "HIGH");
        createOmakase("????????? ??????", "?????????", "MIDDLE");
        createOmakase("????????? ????????????", "?????????", "MIDDLE");
        createOmakase("????????? ??????", "?????????", "ENTRY");
        createOmakase("????????? ??????", "?????????", "ENTRY");
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void searchOmakase() throws Exception {
        mvc.perform(get("/omakases?size=2&page=1")
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void searchOmakase2() throws Exception {
        mvc.perform(get("/omakases?size=2&page=0")
                .param("level","HIGH")
                .param("keyword","?????????")
        ).andExpect(status().isOk()).andDo(print());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void searchOmakase3() throws Exception {
        mvc.perform(get("/omakases?size=2&page=0")
                .param("level","ENTRY")
                .param("keyword","?????????")
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
                        .address("????????? ????????? ????????? ????????? ???????????? ??????")
                        .county(county)
                        .phoneNumber("03211110000")
                        .description("12??? ?????? ????????????")
                        .businessHours("??????: 11:00~13:30 / ??????: 18:00~20:30")
                        .priceInformation("??????: 98,000 / ??????: 130,000")
                        .category(Category.SUSHI)
                        .holiday(Holiday.MONDAY)
                        .level(Level.valueOf(level))
                        .build()
        );
        return omakase;
    }*/

}
