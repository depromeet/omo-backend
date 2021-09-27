package com.depromeet.omobackend.domain.omakase;

import com.depromeet.omobackend.domain.bookmark.Bookmark;
import com.depromeet.omobackend.domain.location.Location;
import com.depromeet.omobackend.domain.stamp.Stamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Time;
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
    private String country;

    @Column(length = 11)
    private String phoneNumber;

    private String photoUrl;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Level level;

    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    private Category category;

    private Time openTime;

    private Time closeTime;

    @Column(columnDefinition = "enum('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')")
    private Holiday holiday;

    @OneToMany(mappedBy = "omakase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stamp> stamps;

    @OneToMany(mappedBy = "omakase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks;

    @OneToOne(mappedBy = "omakase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Location location;

    @Builder
    public Omakase(String name, String address, String country, String phoneNumber, String photoUrl, String description, Level level, Category category, Time openTime, Time closeTime, Holiday holiday, List<Stamp> stamps, List<Bookmark> bookmarks, Location location) {
        this.name = name;
        this.address = address;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.description = description;
        this.level = level;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.holiday = holiday;
        this.stamps = stamps;
        this.bookmarks = bookmarks;
        this.location = location;
    }

}
