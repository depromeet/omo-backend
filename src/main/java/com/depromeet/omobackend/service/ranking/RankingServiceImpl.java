package com.depromeet.omobackend.service.ranking;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.RankingDto;
import com.depromeet.omobackend.dto.response.RankingInCountyDto;
import com.depromeet.omobackend.repository.ranking.RankingRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.service.ranking.RankingService;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RankingServiceImpl implements RankingService {
    private final AuthenticationUtil authenticationUtil;
    private final StampRepository stampRepository;
    private final RankingRepository rankingRepository;

    @Override
    public RankingDto getMyRanking() {
        User user = authenticationUtil.getUser();
        return null;
    }

    @Override
    public List<RankingDto> getRankers(Long limit) {
        return null;
    }

    @Override
    public RankingInCountyDto getMyCountyRanking() {
        return null;
    }
}
