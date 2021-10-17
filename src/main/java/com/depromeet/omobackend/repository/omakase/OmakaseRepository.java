package com.depromeet.omobackend.repository.omakase;

import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OmakaseRepository extends CrudRepository<Omakase, Long> {
    @Query("select distinct o from Omakase o where o.level = ?1 and (o.name like ?2 or o.county like ?2) order by o.recommendationCount desc, o.name")
    List<Omakase> findDistinctByLevelAndNameLikeAndCountyLike(Level level, String keyword);
}
