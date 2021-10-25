package com.depromeet.omobackend.domain.location;

import com.depromeet.omobackend.domain.omakase.Omakase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {

    @Id
    private Long omakaseId;

    @Column(columnDefinition = "decimal(15, 10)", nullable = false)
    private BigDecimal latitude;

    @Column(columnDefinition = "decimal(15, 10)", nullable = false)
    private BigDecimal longitude;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "omakase_id", nullable = false)
    private Omakase omakase;

    @Builder
    public Location(BigDecimal latitude, BigDecimal longitude, Omakase omakase) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.omakase = omakase;
    }
}
