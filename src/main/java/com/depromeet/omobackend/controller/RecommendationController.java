package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.service.recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/recommendation/{id}")
    public boolean updateRecommendationStatus(@PathVariable("id") long omakaseId) {
        return recommendationService.updateRecommendationStatus(omakaseId);
    }

}
