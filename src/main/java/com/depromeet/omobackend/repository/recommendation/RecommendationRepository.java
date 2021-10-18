package com.depromeet.omobackend.repository.recommendation;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.recommendation.RecommendationId;
import com.depromeet.omobackend.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends CrudRepository<Recommendation, RecommendationId> {
    Optional<Recommendation> findByUserAndOmakase(User user, Omakase omakase);
    List<Recommendation> findAllByOmakase(Omakase omakase);
}
