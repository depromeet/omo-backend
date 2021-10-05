package com.depromeet.omobackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StampsDto {

    private Long id;

    private LocalDateTime createdDate;

}
