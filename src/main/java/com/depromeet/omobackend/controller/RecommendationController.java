package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.service.recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * 좋아요 상태 업데이트
     * @param omakaseId
     * @return
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/recommendation/{id}")
    public boolean updateRecommendationStatus(@PathVariable("id") long omakaseId) {
        return recommendationService.updateRecommendationStatus(omakaseId);
    }

}
