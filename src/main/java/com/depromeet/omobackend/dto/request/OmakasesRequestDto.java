package com.depromeet.omobackend.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OmakasesRequestDto {

    private Long omakaseId;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiptIssuanceDate;
}
