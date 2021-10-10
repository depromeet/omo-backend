package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.dto.response.OmakaseDetailsResponse;
import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;

public interface OmakaseService {
    OmakaseSearchResultResponse searchOmakase(String level, String keyword);
    OmakaseDetailsResponse getOmakase(long id);
}
