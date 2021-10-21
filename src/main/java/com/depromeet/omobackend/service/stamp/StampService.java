package com.depromeet.omobackend.service.stamp;

import com.depromeet.omobackend.domain.stamp.Stamp;
import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
import com.depromeet.omobackend.dto.request.StampUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StampService {
    String saveReceipt(MultipartFile receiptImageFile) throws IOException;
    Stamp saveStamp(StampSaveRequestDto requestDto);
    Long isCertifiedUpdate(Long id, StampUpdateRequestDto requestDto);
}
