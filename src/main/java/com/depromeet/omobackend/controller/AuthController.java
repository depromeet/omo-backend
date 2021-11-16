package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    /**
     * 로그아웃
     */
    @DeleteMapping("/logout")
    public void logout() {
        authService.logout();
    }

}
