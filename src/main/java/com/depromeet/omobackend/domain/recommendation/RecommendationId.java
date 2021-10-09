package com.depromeet.omobackend.domain.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationId implements Serializable {
    @Id
    private Long user;
    @Id
    private Long omakase;
}
