package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.OmakaseRegistrationRequest;
import com.depromeet.omobackend.dto.request.StampUpdateRequestDto;
import com.depromeet.omobackend.security.admin.AdminService;
import com.depromeet.omobackend.service.stamp.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * 관리자 페이지
 */
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final StampService stampService;
    private final AdminService adminService;

    /**
     * 관리자 도장 인증 수락
     * @param id
     * @param requestDto
     * @return
     */
    @PutMapping("/stamp/{id}")
    public Long update(@PathVariable Long id, @RequestBody StampUpdateRequestDto requestDto) {
        return stampService.isCertifiedUpdate(id, requestDto);
    }

    @PostMapping("/registration/omakase")
    public long registrationOmakase(@RequestBody @Valid OmakaseRegistrationRequest request) {
        return adminService.registrationOmakase(request);
    }

    @PatchMapping("/registration/omakase/{id}")
    public void registrationOmakase(@PathVariable Long id, @RequestParam MultipartFile file) {
        adminService.registrationOmakase(id, file);
    }

}
