package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.response.RankingDto;
import com.depromeet.omobackend.service.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RankingController {
    private final RankingService rankingService;

    @GetMapping(path = "/my-ranking")
    public RankingDto getMyRanking() {
        return rankingService.getMyRanking();
    }

    @GetMapping(path = "/rankers")
    public List<RankingDto> getRankers(@RequestParam(defaultValue = "10") int limit){
        return rankingService.getRankers(limit);
    }

//    @GetMapping(path = "/my-county-ranking")
//    public RankingInCountyDto getMyCountyRanking() {
//        return rankingService.getMyCountyRanking();
//    }
}
