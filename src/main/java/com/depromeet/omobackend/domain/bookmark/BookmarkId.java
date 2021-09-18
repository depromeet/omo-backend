package com.depromeet.omobackend.domain.bookmark;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkId implements Serializable {

    @Id
    private Long userId;

    @Id
    private Long omakaseId;


}
