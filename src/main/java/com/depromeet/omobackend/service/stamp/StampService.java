package com.depromeet.omobackend.service.stamp;

import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
import com.depromeet.omobackend.dto.request.StampUpdateRequestDto;
import com.depromeet.omobackend.dto.response.StampsCountResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public interface StampService {
    StampsCountResponseDto getStampCount();
    String saveReceipt(MultipartFile receiptImageFile) throws IOException;
    Stamp saveStamp(Long omakaseId, LocalDate receiptIssuanceDate, StampSaveRequestDto requestDto);
    Long isCertifiedUpdate(Long id, StampUpdateRequestDto requestDto);
}
