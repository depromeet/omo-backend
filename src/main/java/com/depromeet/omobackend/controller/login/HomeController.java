package com.depromeet.omobackend.controller.login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.Principal;

@RestController
public class HomeController {

    @PostMapping(value = "/api/hello")
    public ResponseEntity<String> test(HttpServlet resp, Principal principal) throws IOException {
        String nickname = "Annonymous";

        if (!principal.getName().isEmpty()) {
            OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getDetails();
            nickname = user.getAttribute("nickname").toString();
        }
        return ResponseEntity.ok(nickname);
    }
}
