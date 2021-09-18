package com.depromeet.omobackend.repository.location;

import com.depromeet.omobackend.domain.location.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
