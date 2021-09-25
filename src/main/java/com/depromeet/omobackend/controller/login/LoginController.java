package com.depromeet.omobackend.controller.login;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class LoginController {
    @Deprecated
    @RequestMapping(value="/main.do")
    public String mainPage(HttpServletRequest req, HttpServletResponse resp, OAuth2AuthenticationToken auth, Principal principal) {

        return "main.html";
    }

    // 로그인 페이지
    @GetMapping(value="/oauth2Login")
    public String loginPage() {

        return "index.html";
    }
}
