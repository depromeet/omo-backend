package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.StampOmakaseRequestDto;
import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
import com.depromeet.omobackend.dto.response.StampsCountResponseDto;
import com.depromeet.omobackend.service.stamp.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


/**
 * 도장 깨기
 */
@RequiredArgsConstructor
@RestController
public class StampController {

    public static final String SUCCESS = "SUCCESS";
    private final StampService stampService;

    /**
     * 회원 닉네임, 프로필 이미지, 도장 개수 조회
     * @return
     */
    @GetMapping("/stamp-count")
    public StampsCountResponseDto getStampsCount() {
        return stampService.getStampCount();
    }

    /**
     * Stamp 등록 (미인증 상태)
     * @param omakasesRequestDto
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/stamp")
    public ResponseEntity<String> saveStamp(StampOmakaseRequestDto omakasesRequestDto,
                                            @RequestParam("receiptImage") MultipartFile multipartFile) throws IOException {

        String saveFileName = stampService.saveReceipt(multipartFile);
        StampSaveRequestDto requestDto = new StampSaveRequestDto();
        requestDto.setFileUrl(saveFileName);

        stampService.saveStamp(omakasesRequestDto.getOmakaseId(), omakasesRequestDto.getReceiptIssuanceDate(), requestDto);

        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    /**
     * 영수증 사진 Form 업로드 테스트 화면. 추후 삭제 예정
     * @return
     */
    @GetMapping("/stamp/upload")
    public ModelAndView uploadedReceiptFile() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("receiptUpload.html");
        return mv;
    }
}
