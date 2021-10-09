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

import java.util.LinkedList;
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
        long stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();
        RankingDto rankingDto = RankingDto.builder()
                .ranking((long) rankingRepository.getRankersMoreThanUserStamp(stampCount).size() + 1)
                .nickname(user.getNickname())
                .stampCount(stampCount)
                .build();
        return rankingDto;
    }

    @Override
    public List<RankingDto> getRankers(int limit) {
        List<User> userList = rankingRepository.getRankers(PageRequest.of(0, limit));
        List<RankingDto> rankers = new LinkedList<>();
        for (int i = 1; i < userList.size(); i++){
            rankers.add(
                RankingDto.builder()
                        .ranking((long)i)
                        .nickname(userList.get(i).getNickname())
                        .stampCount((long) stampRepository.findAllByUserAndIsCertifiedTrue(userList.get(i)).size())
                        .build()
            );
        }
        return rankers;
    }

    @Override
    public RankingInCountyDto getMyCountyRanking() {
        return null;
    }
}
