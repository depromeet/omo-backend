package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping(value="/login")
    public String loginPage() {
        return "loginTest.html";
    }

    @DeleteMapping("/logout")
    public void logout() {
        loginService.logout();
    }
}
