package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.dto.request.UserSaveRequestDto;
import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.UserInfoResponse;
import com.depromeet.omobackend.dto.response.UserSaveResponseDto;
import com.depromeet.omobackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Value("${profile.upload.directory}")
    private String profileUploadPath;

    /**
     * 회원 정보 조회
     * @param email
     * @return userInfoResponse
     */
    @GetMapping({"/user/{email}", "/user"})
    public UserInfoResponse getUserInfo(@PathVariable(required = false) String email) {
        return userService.getUserInfo(email);
    }

    /**
     * My Omakase 조회
     * @param email
     * @return
     */
    @GetMapping({"/my-omakase/{email}", "/my-omakase"})
    public MyOmakasesResponse getMyOmakases(@PathVariable(required = false) String email) {
        return userService.getMyOmakases(email);
    }

    /**
     * 회원 가입
     * @param requestDto
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/user")
    public ResponseEntity<UserSaveResponseDto> save(UserSaveRequestDto requestDto,
                                                    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        UserSaveResponseDto userSaveResponseDto = userService.saveAccount(requestDto, multipartFile);
        return new ResponseEntity<>(userSaveResponseDto, HttpStatus.OK);
    }

    /**
     * 회원 프로필 이미지 조회
     * @return
     * @throws Exception
     */
    @GetMapping("/user/profile")
    public ResponseEntity<byte[]> userProfileView(String email) throws Exception {
        ResponseEntity<byte[]> entity = userService.getProfileView(email);
        return entity;
    }


    /**
     * 회원 닉네임 중복 체크
     * @param nickname
     */
    @GetMapping("/user/check")
    public void checkNicknameDuplicate(@RequestParam String nickname) {
        userService.checkNicknameDuplicate(nickname);
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/user")
    public void deleteAccount() {
        userService.deleteAccount();
    }

    /**
     * 회원 닉네임 변경
     * @param request
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/user")
    public void modifyNickname(@RequestBody @Valid ModifyNicknameRequest request) {
        userService.modifyNickname(request.getNickname());
    }

    /**
     * Profile Upload 테스트 화면. 추후 삭제 예정
     * @return
     */
    @GetMapping("/profile")
    public ModelAndView getTestPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sign-up.html");
        return mv;
    }
}
