package com.depromeet.omobackend.repository.ranking;

import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RankingRepository extends CrudRepository<User, Long> {
    @Query(value = "select u.* from user u join stamp s on u.id = s.user_id and s.is_certified = true group by u.id order by count(u.id) desc, u.last_stamp_date, u.nickname desc limit ?1"
    , nativeQuery = true
    )
    List<User> getRankers(Integer limit);

    @Query(value = "SELECT COUNT(*) FROM " +
            "(SELECT u.nickname, u.last_stamp_date, COUNT(u.nickname) stamps FROM user u JOIN stamp s ON u.id = s.user_id AND s.is_certified = TRUE " +
            "GROUP BY u.nickname " +
            "having (stamps > ?1) OR (stamps = ?1 AND u.last_stamp_date < ?2) OR (stamps = ?1 AND u.last_stamp_date = ?2 AND u.nickname < ?3)) sub"
    , nativeQuery = true)
    Long getRankersMoreThanUserStamp(Integer stampCount, LocalDate lastStampDate, String nickname);

//    @Query("select u from User u join Stamp s where s.isCertified = true group by u having count(u) > ?1 and u.county = ?2")
//    List<User> getRankersMoreThanUserStampAndSameCounty(Long stampCount, String county);
}
