package com.depromeet.omobackend.repository.omakase;

import com.depromeet.omobackend.domain.omakase.Level;
import com.depromeet.omobackend.domain.omakase.Omakase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OmakaseRepository extends CrudRepository<Omakase, Long> {
    List<Omakase> findAllByLevelAndCountyLikeOrderByStampsDescName(Level level, String county);
    List<Omakase> findAllByLevelAndNameLikeOrderByStampsDescName(Level level, String name);
}
