package com.depromeet.omobackend.repository.omakase;

import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OmakaseRepository extends CrudRepository<Omakase, Long> {
    List<Omakase> findAllByLevelAndCountyLikeOrderByRecommendationsDescName(Level level, String county);
    List<Omakase> findAllByLevelAndNameLikeOrderByRecommendationsDescName(Level level, String name);
}
