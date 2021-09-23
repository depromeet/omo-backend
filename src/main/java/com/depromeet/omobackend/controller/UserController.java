package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @PatchMapping("/user")
    public void modifyNickname(@RequestBody @Valid ModifyNicknameRequest request) {
        userService.modifyNickname(request.getNickname());
    }

}
