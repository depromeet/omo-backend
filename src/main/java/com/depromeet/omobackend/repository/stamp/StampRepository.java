package com.depromeet.omobackend.repository.stamp;

import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StampRepository extends CrudRepository<Stamp, Long> {
    List<Stamp> findAllByUserAndIsCertifiedTrue(User user);
    List<Stamp> findByUserOrderByCreatedDateDesc(User user);
}
