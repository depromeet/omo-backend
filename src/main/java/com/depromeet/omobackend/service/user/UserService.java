package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.dto.request.UserSaveRequestDto;
import com.depromeet.omobackend.dto.response.MypageResponse;

public interface UserService {
    User saveAccount(UserSaveRequestDto userDto);
    void deleteAccount();
    void modifyNickname(String nickname);
    MypageResponse getMyPage(String email);
}
