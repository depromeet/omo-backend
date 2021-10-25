package com.depromeet.omobackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampUpdateRequestDto {
    private Boolean isCertified;            // 인증 여부
    private LocalDate receiptIssuanceDate;  // 영수증 발급일
}
