package com.depromeet.omobackend.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StampOmakaseRequestDto {

    private Long omakaseId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiptIssuanceDate;
}
