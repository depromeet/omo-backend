package com.depromeet.omobackend.repository.omakase;

import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OmakaseRepository extends CrudRepository<Omakase, Long> {
    @Query(value = "select distinct o from Omakase o where o.level = ?1 and (o.name like ?2 or o.county like ?2) order by o.recommendationCount desc")
    List<Omakase> findDistinctByLevelAndNameLikeAndCountyLike(Level level, String keyword, Pageable page);

    @Query("select distinct o from Omakase o where (o.name like ?1 or o.county like ?1) order by o.recommendationCount desc, o.name")
    List<Omakase> findDistinctByNameLikeAndCountyLike(String keyword, Pageable page);
}
