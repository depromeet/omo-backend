package com.depromeet.omobackend.domain.user;

import com.depromeet.omobackend.domain.recommendation.Recommendation;
import com.depromeet.omobackend.domain.stamp.Stamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false, unique = true)
    private String nickname;

    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean isActivated;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDate lastStampDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stamp> stamps;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Recommendation> recommendations;

    @Builder
    public User(String nickname, String email, String profileUrl, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.isActivated = true;
        this.createdDate = LocalDateTime.now();
        this.role = role;
    }

    public User modifyNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public User modifyProfile(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public void isNotActivated() {
        this.isActivated = false;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void lastStampDateUpdate(LocalDate userCurrentStampDate, LocalDate lastStampDate) {
        if (lastStampDate.isAfter(userCurrentStampDate)) {
            this.lastStampDate = lastStampDate;
        } else {
            this.lastStampDate = userCurrentStampDate;
        }
    }

}
