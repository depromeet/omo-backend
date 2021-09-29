package com.depromeet.omobackend.controller.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(value="/oauth2Login")
    public String loginPage() {
        return "loginTest.html";
    }
}
