package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.response.MypageResponse;

public interface UserService {
//    User saveAccount(UserDto userDto);
    void checkNicknameDuplicate(String nickname);
    void deleteAccount();
    void modifyNickname(String nickname);
    MypageResponse getMyPage(String email);
}
