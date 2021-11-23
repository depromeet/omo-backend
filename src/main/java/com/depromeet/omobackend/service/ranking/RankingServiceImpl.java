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
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Service
public class RankingServiceImpl implements RankingService {
    private final AuthenticationUtil authenticationUtil;
    private final StampRepository stampRepository;
    private final RankingRepository rankingRepository;

    /*@Override
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
    }*/

    @Override
    public List<RankingDto> getRankers(int limit) {
        List<User> userList = rankingRepository.getRankers(limit);
        AtomicLong ranking = new AtomicLong(1);
        List<RankingDto> rankingDtoList = new LinkedList<>();
        for (User user : userList){
            Integer stampCount = stampRepository.findAllByUserAndIsCertifiedTrue(user).size();
            rankingDtoList.add(
                    RankingDto.builder()
                            .ranking(ranking.getAndIncrement())
                            .nickname(user.getNickname())
                            .stampCount(stampCount)
                            .profileUrl(user.getProfileUrl())
                            .email(user.getEmail())
                            .power(getPower(stampCount))
                            .build()
            );
        }
        return rankingDtoList;
    }

    private Integer getPower(Integer stampCount) {
        if (stampCount < 2) return stampCount;
        else if (stampCount <= 4) return 2;
        else if (stampCount <= 9) return 3;
        else if (stampCount <= 19) return 4;
        else return 5;
    }
//    @Override
//    public RankingInCountyDto getMyCountyRanking() {
//        return null;
//    }
}
