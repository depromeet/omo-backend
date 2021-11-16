package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.response.RankingDto;
import com.depromeet.omobackend.service.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RankingController {
    private final RankingService rankingService;

    /**
     * 랭킹 조회
     * @return
     */
    @GetMapping(path = "/my-ranking")
    public RankingDto getMyRanking() {
        return rankingService.getMyRanking();
    }

    /**
     * 랭킹 목록 조회
     * @param limit
     * @return
     */
    @GetMapping(path = "/rankers")
    public List<RankingDto> getRankers(@RequestParam(defaultValue = "10") int limit){
        return rankingService.getRankers(limit);
    }

//    @GetMapping(path = "/my-county-ranking")
//    public RankingInCountyDto getMyCountyRanking() {
//        return rankingService.getMyCountyRanking();
//    }
}
