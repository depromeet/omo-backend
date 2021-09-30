package com.depromeet.omobackend.domain.omakase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {
    HIGH("하이엔드", "100,000 초과"), MIDDLE("미들엔드", "50,000~100,000"), ROW("로우엔드", "50,000 미만");

    private final String name;
    private final String description;
}
