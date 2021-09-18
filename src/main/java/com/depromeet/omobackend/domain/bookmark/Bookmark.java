package com.depromeet.omobackend.domain.bookmark;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BookmarkId.class)
@Entity
public class Bookmark {

    @Id
    private Long userId;

    @Id
    private Long omakaseId;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "omakase_id", nullable = false)
    private Omakase omakase;

    @Builder
    public Bookmark(User user, Omakase omakase) {
        this.user = user;
        this.omakase = omakase;
    }

}
