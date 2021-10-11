package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.user.UserDto;
import com.depromeet.omobackend.repository.user.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원 가입 테스트")
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
    }

}
