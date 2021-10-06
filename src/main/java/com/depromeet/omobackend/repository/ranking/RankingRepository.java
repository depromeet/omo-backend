package com.depromeet.omobackend.repository.ranking;

import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RankingRepository extends CrudRepository<User, Long> {
    @Query("select u from User u join Stamp s where s.isCertified = true group by u order by count(s) desc")
    List<User> getRankers(Pageable pageable);

    @Query("select u from User u join Stamp s where s.isCertified = true group by u having count(s) > ?1")
    List<User> getRankersMoreThanUserStamp(Long stampCount);
}
