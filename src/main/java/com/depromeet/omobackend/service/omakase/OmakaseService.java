package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;
import org.springframework.data.domain.Pageable;

public interface OmakaseService {
    OmakaseSearchResultResponse searchOmakases(Pageable page, String level, String keyword);
    OmakaseDetailsResponse getOmakaseDetail(long id);
    void isCertificatedOmakase(long id);
}
