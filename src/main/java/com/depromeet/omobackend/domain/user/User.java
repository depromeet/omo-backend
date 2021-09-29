package com.depromeet.omobackend.domain.user;

import com.depromeet.omobackend.domain.bookmark.Bookmark;
import com.depromeet.omobackend.domain.stamp.Stamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"nickname","email"})})
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @NotNull
    private String nickname;

    @NotNull
    private String email;

    private String description;

    @NotNull
    private Boolean isActivated;

    private String profileImage;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stamp> stamps;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String nickname, String email, String description, String profileImage, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.description = description;
        this.profileImage = profileImage;
        this.isActivated = true;
        this.createdDate = LocalDateTime.now();
        this.role = role;
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

    public String getRoleKey() {
        return this.role.getKey();
    }

}
