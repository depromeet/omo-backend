package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @DeleteMapping("/user")
    public void deleteAccount() {
        userService.deleteAccount();
    }

    @DeleteMapping("/logout")
    public void logout() {
        userService.logout();
    }

}
