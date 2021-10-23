package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.StampSaveRequestDto;
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

    @GetMapping("/stamp/upload")
    public ModelAndView uploadedReceiptFile() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("receiptUpload.html");
        return mv;
    }

    // 오마카세 ID, 영수증 발급 날짜, 영수증 사진
    @PostMapping("/stamp")
    public ResponseEntity<String> saveStamp(@PathVariable Long omakaseId, @PathVariable LocalDate receiptIssuanceDate,
            @RequestParam("receiptImage") MultipartFile multipartFile) throws IOException {

        StampSaveRequestDto requestDto = new StampSaveRequestDto();
        String saveFileName = stampService.saveReceipt(multipartFile);
        requestDto.setFileUrl(saveFileName);

        stampService.saveStamp(omakaseId, receiptIssuanceDate, requestDto);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    // TODO: Stamp Count 조회
}
