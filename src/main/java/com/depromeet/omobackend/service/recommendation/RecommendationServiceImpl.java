package com.depromeet.omobackend.service.recommendation;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.exception.OmakaseNotFoundException;
import com.depromeet.omobackend.repository.omakase.OmakaseRepository;
import com.depromeet.omobackend.repository.recommendation.RecommendationRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final OmakaseRepository omakaseRepository;
    private final RecommendationRepository recommendationRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public boolean updateRecommendationStatus(long omakaseId) {
        User user = authenticationUtil.getUser();
        Omakase omakase = omakaseRepository.findById(omakaseId)
                .orElseThrow(OmakaseNotFoundException::new);

        Recommendation recommendation = recommendationRepository.findByUserAndOmakase(user, omakase)
                .orElse(null);

        if (recommendation != null) {
            recommendationRepository.delete(recommendation);
            omakaseRepository.save(omakase.minusRecommendationCount());
            return false;
        } else {
            recommendationRepository.save(new Recommendation(user, omakase));
            omakaseRepository.save(omakase.plusRecommendationCount());
            return true;
        }
    }

}
