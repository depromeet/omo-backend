package com.depromeet.omobackend.service.omakase;

import com.depromeet.omobackend.dto.response.OmakaseSearchResultResponse;

public interface OmakaseService {
    OmakaseSearchResultResponse searchOmakase(String keyword);
}
