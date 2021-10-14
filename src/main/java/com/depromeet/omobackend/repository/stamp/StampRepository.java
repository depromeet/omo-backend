package com.depromeet.omobackend.repository.stamp;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StampRepository extends CrudRepository<Stamp, Long> {
    List<Stamp> findAllByUserAndIsCertifiedTrue(User user);
    @Query("select distinct s.omakase from Stamp s where s.user=?1 order by s.createdDate desc")
    List<Stamp> findByUserOrderByCreatedDateDesc(User user);
    List<Stamp> findAllByUserAndIsCertifiedTrueOrderByCreatedDate(User user);
    Optional<Stamp> findFirstByOmakaseOrderByCreatedDateDesc(Omakase omakase);
}
