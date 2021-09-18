package com.depromeet.omobackend.domain.stamp;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime createdDate;

    @NotNull
    private Boolean isCertified;

    @NotNull
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "omakase_id", nullable = false)
    private Omakase omakase;

    public Stamp(String fileUrl, User user, Omakase omakase) {
        this.createdDate = LocalDateTime.now();
        this.isCertified = false;
        this.fileUrl = fileUrl;
        this.user = user;
        this.omakase = omakase;
    }

}
