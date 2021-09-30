package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.service.omakase.OmakaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/omakase")
@RestController
public class OmakaseController {

    private final OmakaseService omakaseService;

    @GetMapping("/search")
    public OmakaseSearchResultResponse searchOmakase(@RequestParam String keyword) {
        return omakaseService.searchOmakase(keyword);
    }

}
