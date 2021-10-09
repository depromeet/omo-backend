package com.depromeet.omobackend.domain.omakase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level { //저녁식사 기준
    HIGH("하이엔드", "200,000원 이상"), MIDDLE("미들", "100,000~199,999원"), CLASSIC("클래식", "100,000 미만");

    private final String name;
    private final String description;
}
