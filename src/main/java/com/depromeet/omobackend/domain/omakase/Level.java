package com.depromeet.omobackend.domain.omakase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level { //저녁식사 기준
    HIGH("하이엔드", "12만원 이상"), MIDDLE("미들", "5만원 이상 12만원 미만"), ENTRY("엔트리", "5만원 미만");

    private final String name;
    private final String description;
}
