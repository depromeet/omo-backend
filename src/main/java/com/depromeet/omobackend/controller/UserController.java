package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.dto.user.UserDto;
import com.depromeet.omobackend.service.user.UserService;
import com.depromeet.omobackend.util.ProfileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {

    @Value("${profile.upload.directory}")
    private String profileUploadPath;

    private final UserService userService;

    @GetMapping("/user")
    public MypageResponse getMyPage(@PathVariable(required = false) String email) {
        return userService.getMyPage(email);
    }

    @PostMapping(value = "/user")
    public void save(@ModelAttribute(name = "user") UserDto user, HttpServletResponse response,
                     @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setProfileImage(fileName);
        User savedUser = userService.saveAccount(user);
        String uploadDir = profileUploadPath + savedUser.getId();

        ProfileUploadUtil.saveProfile(uploadDir, fileName, multipartFile);
    }

    @DeleteMapping("/logout")
    public void logout() {
        userService.logout();
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
}
