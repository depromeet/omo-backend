package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.request.UserSaveRequestDto;
import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.UserInfoResponse;
import com.depromeet.omobackend.dto.response.UserSaveResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public interface UserService {

    UserSaveResponseDto saveAccount(UserSaveRequestDto userDto, MultipartFile multipartFile) throws IOException;
    void checkNicknameDuplicate(String nickname);
    void deleteAccount();
    void modifyNickname(String nickname);
    void modifyProfile(MultipartFile multipartFile) throws IOException;
    void updateLastStampDate(LocalDate lastStampDate);
    UserInfoResponse getUserInfo(String email);
    MyOmakasesResponse getMyOmakases(String email);
    ResponseEntity<byte[]> getProfileView(String email) throws IOException;
}
