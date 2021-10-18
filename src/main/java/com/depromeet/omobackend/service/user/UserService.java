package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.response.MyOmakasesResponse;
import com.depromeet.omobackend.dto.response.UserInfoResponse;

public interface UserService {
//    User saveAccount(UserDto userDto);
    void checkNicknameDuplicate(String nickname);
    void deleteAccount();
    void modifyNickname(String nickname);
    UserInfoResponse getUserInfo(String email);
    MyOmakasesResponse getMyOmakases(String email);
}
