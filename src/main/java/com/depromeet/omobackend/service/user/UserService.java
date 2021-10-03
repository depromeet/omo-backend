package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.response.MypageResponse;
import com.depromeet.omobackend.dto.user.UserDto;

public interface UserService {
    User saveAccount(UserDto userDto);
    void logout();
    void deleteAccount();
    void modifyNickname(String nickname);
    MypageResponse getMyPage(String email);
}
