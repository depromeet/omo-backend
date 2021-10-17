package com.depromeet.omobackend.controller;

import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.UserInfoResponse;
import com.depromeet.omobackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

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

    @GetMapping("/user/check")
    public void checkNicknameDuplicate(@RequestParam String nickname) {
        userService.checkNicknameDuplicate(nickname);
    }

//    @PostMapping(value = "/user")
//    public void save(@ModelAttribute(name = "user") UserDto user, HttpServletResponse response,
//                     @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
//
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        user.setProfileImage(fileName);
//        User savedUser = userService.saveAccount(user);
//        String uploadDir = profileUploadPath + savedUser.getId();
//
//        ImageUploadUtil.saveProfile(uploadDir, fileName, multipartFile);
//    }

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
