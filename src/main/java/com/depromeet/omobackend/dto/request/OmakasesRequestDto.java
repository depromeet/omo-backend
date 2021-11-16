package com.depromeet.omobackend.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmakasesRequestDto {
    private Long omakaseId;
    private LocalDate receiptIssuanceDate;
}
