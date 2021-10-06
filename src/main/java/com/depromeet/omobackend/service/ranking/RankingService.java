package com.depromeet.omobackend.service.ranking;

import com.depromeet.omobackend.dto.response.RankingDto;
import com.depromeet.omobackend.dto.response.RankingInCountyDto;

import java.util.List;

public interface RankingService {
    RankingDto getMyRanking();
    List<RankingDto> getRankers(Long limit);
    RankingInCountyDto getMyCountyRanking();
}
