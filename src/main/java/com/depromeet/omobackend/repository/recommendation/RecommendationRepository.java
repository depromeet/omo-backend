package com.depromeet.omobackend.repository.recommendation;

import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.recommendation.RecommendationId;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends CrudRepository<Recommendation, RecommendationId> {
}
