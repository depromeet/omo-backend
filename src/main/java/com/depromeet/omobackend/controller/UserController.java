package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public MypageResponse getMyPage(@PathVariable(required = false) String email) {
        return userService.getMyPage(email);
    }

//    @PostMapping(value = "/user")
//    public User save(@RequestBody UserDto userDto) {
//        return userService.saveAccount(userDto);
//    }

    @DeleteMapping("/logout")
    public void logout() {
        userService.logout();
    }

    @DeleteMapping("/user")
    public void deleteAccount() {
        userService.deleteAccount();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/user")
    public void modifyNickname(@RequestBody @Valid ModifyNicknameRequest request) {
        userService.modifyNickname(request.getNickname());
    }
}
