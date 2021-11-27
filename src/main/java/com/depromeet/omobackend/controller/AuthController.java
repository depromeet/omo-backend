package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.response.TokenResponse;
import com.depromeet.omobackend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    /**
     * 로그아웃
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public void logout() {
        authService.logout();
    }

    @PatchMapping("/auth")
    public TokenResponse tokenRefresh(@RequestHeader("X-Refresh-Token") String xRefreshToken) {
        return authService.tokenRefresh(xRefreshToken);
    }

}
