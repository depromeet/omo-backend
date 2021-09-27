package com.depromeet.omobackend.domain.user;

import com.depromeet.omobackend.domain.bookmark.Bookmark;
import com.depromeet.omobackend.domain.stamp.Stamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(length = 45, nullable = false, unique = true)
    private String email;

    private String description;

    @NotNull
    private boolean isActivated;

    private String profileImage;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stamp> stamps;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks;

    @Builder
    public User(String nickname, String email, String description, String profileImage) {
        this.nickname = nickname;
        this.email = email;
        this.description = description;
        this.profileImage = profileImage;
        this.isActivated = true;
        this.createdDate = LocalDateTime.now();
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
        this.modifiedDate = LocalDateTime.now();
    }

    public void modifyDescription(String description) {
        this.description = description;
        this.modifiedDate = LocalDateTime.now();
    }

    public void isNotActivated() {
        this.isActivated = false;
    }

}
