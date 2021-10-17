package com.depromeet.omobackend.domain.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationId implements Serializable {

    private Long user;

    private Long omakase;

}
