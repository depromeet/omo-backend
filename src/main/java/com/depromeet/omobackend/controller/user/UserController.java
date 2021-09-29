package com.depromeet.omobackend.controller.user;

import com.depromeet.omobackend.dto.user.UserDto;
import com.depromeet.omobackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/api/user")
    public Long save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }
}
