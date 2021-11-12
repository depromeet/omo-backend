package com.depromeet.omobackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping
    public void test() {
        // cors 확인을 위한 get api를 만들었습니다.
    }

}
