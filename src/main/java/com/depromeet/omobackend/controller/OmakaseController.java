package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import com.depromeet.omobackend.service.omakase.OmakaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class OmakaseController {

    private final OmakaseService omakaseService;

    /**
     * 오마카게 검색
     * @param page
     * @param level
     * @param keyword
     * @return
     */
    @GetMapping("/omakases")
    public OmakaseSearchResultResponse searchOmakases(
            Pageable page,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "%") String keyword) {
        return omakaseService.searchOmakases(page, level, keyword);
    }

    /**
     * 오마카세 상세 정보
     * @param id
     * @return
     */
    @GetMapping("/omakase/{id}")
    public OmakaseDetailsResponse getOmakase(@PathVariable long id) {
        return omakaseService.getOmakaseDetail(id);
    }

    @GetMapping("/omakase/check")
    public void isCertificatedOmakase(@RequestParam long id) {
        omakaseService.isCertificatedOmakase(id);
    }

}
