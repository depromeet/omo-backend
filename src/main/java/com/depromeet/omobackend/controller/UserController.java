package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.dto.request.UserSaveRequestDto;
import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.UserInfoResponse;
import com.depromeet.omobackend.service.user.UserService;
import com.depromeet.omobackend.util.ImageUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
public class UserController {

    public static final String STATUS = "status";
    public static final String MD_5 = "MD5";
    public static final String UTF_8 = "UTF-8";

    @Value("${profile.upload.directory}")
    private String profileUploadPath;

    private final UserService userService;

    @GetMapping({"/user/{email}", "/user"})
    public UserInfoResponse getUserInfo(@PathVariable(required = false) String email) {
        return userService.getUserInfo(email);
    }

    @GetMapping({"/my-omakase/{email}", "/my-omakase"})
    public MyOmakasesResponse getMyOmakases(@PathVariable(required = false) String email) {
        return userService.getMyOmakases(email);
    }

    @PostMapping(value = "/user")
    public ModelAndView save(UserSaveRequestDto requestDto,
                             @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String email = requestDto.getEmail();
        String hashFileName = getHashingFileName(email, fileName);

        requestDto.setProfileUrl(hashFileName);

        User savedUser = userService.saveAccount(requestDto);

        String uploadDir = profileUploadPath + savedUser.getId();
        ImageUploadUtil.saveProfile(uploadDir, fileName, multipartFile);

        // TODO: 토큰 발급 및 리턴 추가 구현 필요
        ModelAndView modelAndView = new ModelAndView("/home");
        modelAndView.addObject(STATUS, "signUp");
        return modelAndView;
    }

    private String getHashingFileName(String email, String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        try {
            MessageDigest md = MessageDigest.getInstance(MD_5);
            md.update(email.getBytes(UTF_8), 0, email.length());
            return new BigInteger(1, md.digest()).toString(16) + fileName;
        } catch (NoSuchAlgorithmException e) {
            return fileName;
        } catch (UnsupportedEncodingException e) {
            return fileName;
        }
    }

    @GetMapping("/user/check")
    public void checkNicknameDuplicate(@RequestParam String nickname) {
        userService.checkNicknameDuplicate(nickname);
    }

    @DeleteMapping("/user")
    public void deleteAccount() {
        userService.deleteAccount();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/user")
    public void modifyNickname(@RequestBody @Valid ModifyNicknameRequest request) {
        userService.modifyNickname(request.getNickname());
    }

    // TODO: 프로필 이미지 업로드 테스트를 위한 HTML File. 테스트 이후 삭제 예정
    @GetMapping("/profile")
    public ModelAndView getTestPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("profileTest.html");
        return mv;
    }
}
