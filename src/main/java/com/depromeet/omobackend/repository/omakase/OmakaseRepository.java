package com.depromeet.omobackend.repository.omakase;

import com.depromeet.omobackend.domain.omakase.Omakase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OmakaseRepository extends CrudRepository<Omakase, Long> {
    List<Omakase> findAllByCountryLikeOrderByName(String country);
    List<Omakase> findAllByNameLikeOrderByName(String name);
}
