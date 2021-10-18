package com.depromeet.omobackend.service.ranking;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.RankingDto;
import com.depromeet.omobackend.dto.response.RankingInCountyDto;
import com.depromeet.omobackend.repository.ranking.RankingRepository;
import com.depromeet.omobackend.repository.stamp.StampRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Service
public class RankingServiceImpl implements RankingService {
    private final AuthenticationUtil authenticationUtil;
    private final StampRepository stampRepository;
    private final RankingRepository rankingRepository;

    @Override
    public RankingDto getMyRanking() {
        User user = authenticationUtil.getUser();
        Integer stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();
        RankingDto rankingDto = RankingDto.builder()
                .ranking(rankingRepository.getRankersMoreThanUserStamp(stampCount, user.getLastStampDate(), user.getNickname()) + 1)
                .nickname(user.getNickname())
                .stampCount(stampCount)
                .profileUrl(user.getProfileUrl())
                .build();
        return rankingDto;
    }

    @Override
    public List<RankingDto> getRankers(int limit) {
        List<User> userList = rankingRepository.getRankers(PageRequest.of(0, limit));
        AtomicLong ranking = new AtomicLong(1);
        return (List<RankingDto>) userList.stream().map(user ->
                RankingDto.builder()
                    .ranking(ranking.getAndIncrement())
                    .nickname(user.getNickname())
                    .stampCount(stampRepository.findAllByUserAndIsCertifiedTrue(user).size())
                    .profileUrl(user.getProfileUrl())
                .build()
        );
    }

//    @Override
//    public RankingInCountyDto getMyCountyRanking() {
//        return null;
//    }
}
