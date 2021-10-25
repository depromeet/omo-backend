package com.depromeet.omobackend.dto.request;

import com.depromeet.omobackend.domain.omakase.Omakase;
import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StampSaveRequestDto {
    private LocalDate receiptIssuanceDate; // 영수증 발급일
    private String fileUrl;
    private User user;
    private Omakase omakase;

    @Builder
    public StampSaveRequestDto(LocalDate receiptIssuanceDate, String fileUrl,
                               User user, Omakase omakase) {
        this.receiptIssuanceDate = receiptIssuanceDate;
        this.fileUrl = fileUrl;
        this.user = user;
        this.omakase = omakase;
    }

    public Stamp toEntity() {
        return Stamp.builder()
                .receiptIssuanceDate(receiptIssuanceDate)
                .fileUrl(fileUrl)
                .user(user)
                .omakase(omakase)
                .build();
    }
}
