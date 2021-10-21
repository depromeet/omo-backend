package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.StampUpdateRequestDto;
import com.depromeet.omobackend.service.stamp.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 페이지
 */
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final StampService stampService;

    // 관리자 도장 인증 수락
    @PutMapping("/stamp/{id}")
    public Long update(@PathVariable Long id, @RequestBody StampUpdateRequestDto requestDto) {
        return stampService.isCertifiedUpdate(id, requestDto);
    }
}
