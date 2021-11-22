package com.depromeet.omobackend.repository.ranking;

import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RankingRepository extends CrudRepository<User, Long> {
    @Query("select u from User u join Stamp s on s.isCertified = true group by u order by count(s) desc, u.lastStampDate desc, u.nickname asc")
    List<User> getRankers();

    @Query(value = "SELECT COUNT(*) FROM " +
            "(SELECT u.nickname, u.last_stamp_date, COUNT(u.nickname) stamps FROM user u JOIN stamp s ON u.id = s.user_id AND s.is_certified = TRUE " +
            "GROUP BY u.nickname " +
            "having (stamps > ?1) OR (stamps = ?1 AND u.last_stamp_date < ?2) OR (stamps = ?1 AND u.last_stamp_date = ?2 AND u.nickname < ?3)) sub"
    , nativeQuery = true)
    Long getRankersMoreThanUserStamp(Integer stampCount, LocalDate lastStampDate, String nickname);

//    @Query("select u from User u join Stamp s where s.isCertified = true group by u having count(u) > ?1 and u.county = ?2")
//    List<User> getRankersMoreThanUserStampAndSameCounty(Long stampCount, String county);
}
