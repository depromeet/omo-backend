package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.domain.user.Role;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    /*@Autowired
    private TestRestTemplate restTemplate;*/

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;

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

        userRepository.save(
                User.builder()
                        .nickname("야호야호야호")
                        .email("test1234@gmail.com")
                        .profileUrl("asdf")
                        .role(Role.USER)
                        .build()
        );
    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    public void checkNicknameDuplicate() throws Exception {
        mvc.perform(get("/user/check")
                .param("nickname","헝거게임")
                .characterEncoding("UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void checkNicknameDuplicate_409() throws Exception {
        mvc.perform(get("/user/check")
                .param("nickname","테스트")
                .characterEncoding("UTF-8")
        ).andExpect(status().isConflict());
    }

    @WithMockUser(value = "test@gmail.com")
    @Test
    public void modifyNickname() throws Exception {
        mvc.perform(patch("/user")
                .content(new ObjectMapper().writeValueAsString(new ModifyNicknameRequest("헬로헬로")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    /*@DisplayName("회원 가입 테스트")
    @Test
    @WithAnonymousUser  // 스프링 시큐리티가 적용된 상태에서 테스트 코드를 돌리기 위한 MockUser
    public void saveUser() throws Exception {
        String nickname = "yunyoung1819";
        String email = "yunyoung@gmail.com";
        UserDto userDto = UserDto.builder()
                .nickname(nickname)
                .email(email)
                .build();

        String url = "http://localhost:8080/user";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, userDto, Long.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<User> userList = (List<User>) userRepository.findAll();
        assertThat(userList.get(0).getNickname()).isEqualTo(nickname);
        assertThat(userList.get(0).getEmail()).isEqualTo(email);
    }*/

}
