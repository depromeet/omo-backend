package com.depromeet.omobackend.domain.omakase;

import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.location.Location;
import com.depromeet.omobackend.domain.stamp.Stamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Omakase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String county;

    @Column(length = 11)
    private String phoneNumber;

    private String photoUrl;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 7, nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Category category;

    @Column(length = 100, nullable = false)
    private String priceInformation;

    @Column(length = 100, nullable = false)
    private String businessHours;

    @Column(nullable = false)
    private Long recommendationCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Holiday holiday;

    @OneToMany(mappedBy = "omakase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stamp> stamps;

    @OneToMany(mappedBy = "omakase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Recommendation> recommendations;

    @OneToOne(mappedBy = "omakase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Location location;

    @Builder
    public Omakase(String name, String address, String county, String phoneNumber, String photoUrl, String description, Level level, Category category, String priceInformation, String businessHours, Holiday holiday) {
        this.name = name;
        this.address = address;
        this.county = county;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.description = description;
        this.level = level;
        this.priceInformation = priceInformation;
        this.businessHours = businessHours;
        this.category = category;
        this.holiday = holiday;
        this.recommendationCount = 0L;
        this.location = new Location(BigDecimal.ONE, BigDecimal.ONE, this);
    }

    public Omakase plusRecommendationCount() {
        this.recommendationCount++;
        return this;
    }

    public Omakase minusRecommendationCount() {
        this.recommendationCount--;
        return this;
    }

}
