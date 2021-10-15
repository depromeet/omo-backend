package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.service.omakase.OmakaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class OmakaseController {

    private final OmakaseService omakaseService;

    @GetMapping("/omakases")
    public OmakaseSearchResultResponse searchOmakase(
            @RequestParam(defaultValue = "MIDDLE") String level,
            @RequestParam(defaultValue = "%") String keyword) {
        return omakaseService.searchOmakase(level, keyword);
    }

    @GetMapping("/omakase/{id}")
    public OmakaseDetailsResponse getOmakase(@PathVariable long id) {
        return omakaseService.getOmakase(id);
    }

}
