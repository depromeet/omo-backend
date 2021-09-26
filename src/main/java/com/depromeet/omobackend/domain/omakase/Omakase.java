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

    private String name;

    private String address;

    private String country;

    private String phoneNumber;

    private String photoUrl;

    private String description;

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
    public Omakase(String name, String address, String description, String country, String phoneNumber, String photoUrl, Category category, Time openTime, Time closeTime, Holiday holiday) {
        this.name = name;
        this.address = address;
        this.country = country;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.holiday = holiday;
    }
}
