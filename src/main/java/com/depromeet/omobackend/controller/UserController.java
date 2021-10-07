package com.depromeet.omobackend.controller;


import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.ModifyNicknameRequest;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.dto.user.UserDto;
import com.depromeet.omobackend.service.user.UserService;
import com.depromeet.omobackend.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public MypageResponse getMyPage(@PathVariable(required = false) String email) {
        return userService.getMyPage(email);
    }

    @PostMapping(value = "/user")
    public RedirectView save(UserDto userDto, @RequestParam("image") MultipartFile multipartFile) {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        userDto.setProfileImage(fileName);

        User savedUser = userService.saveAccount(userDto);

        String uploadDir = "/user-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/users", true);

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
