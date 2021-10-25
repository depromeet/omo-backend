package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
import com.depromeet.omobackend.dto.response.StampsCountResponseDto;
import com.depromeet.omobackend.service.stamp.StampService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;


/**
 * 도장 깨기
 */
@RequiredArgsConstructor
@Controller
public class StampController {

    private final StampService stampService;

    /**
     * 회원 닉네임, 프로필 이미지, 도장 개수 조회
     * @return
     */
    @GetMapping("/stamps-count")
    public StampsCountResponseDto getStampsCount() {
        return stampService.getStampCount();
    }

    /**
     * 도장 등록 (미인증 상태)
     * @param omakaseId (오마카세 ID)
     * @param receiptIssuanceDate (영수증 발급 날짜)
     * @param multipartFile (영수증 사진)
     * @return
     * @throws IOException
     */
    @PostMapping("/stamp")
    public ResponseEntity<String> saveStamp(@PathVariable Long omakaseId, @PathVariable LocalDate receiptIssuanceDate,
            @RequestParam("receiptImage") MultipartFile multipartFile) throws IOException {

        StampSaveRequestDto requestDto = new StampSaveRequestDto();
        String saveFileName = stampService.saveReceipt(multipartFile);
        requestDto.setFileUrl(saveFileName);

        stampService.saveStamp(omakaseId, receiptIssuanceDate, requestDto);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
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
