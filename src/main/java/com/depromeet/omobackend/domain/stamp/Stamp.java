package com.depromeet.omobackend.domain.stamp;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "omakase_id"}))
public class Stamp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDate receiptIssuanceDate;  // 영수증 발급 날짜

    @Column(nullable = false)
    private Boolean isCertified;

    @Column(nullable = false)
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "omakase_id", nullable = false)
    private Omakase omakase;

    @Builder
    public Stamp(LocalDate receiptIssuanceDate, String fileUrl, User user, Omakase omakase) {
        this.createdDate = LocalDateTime.now();
        this.isCertified = false;
        this.receiptIssuanceDate = receiptIssuanceDate;
        this.fileUrl = fileUrl;
        this.user = user;
        this.omakase = omakase;
    }

    public void update(Boolean isCertified, LocalDate receiptIssuanceDate) {
        this.isCertified = isCertified;
        this.receiptIssuanceDate = receiptIssuanceDate;
    }
}
