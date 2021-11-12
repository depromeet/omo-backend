package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.service.recommendation.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/recommendation/{id}")
    public boolean updateRecommendationStatus(@PathVariable("id") long omakaseId) {
        return recommendationService.updateRecommendationStatus(omakaseId);
    }

}
