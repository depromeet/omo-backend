package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.response.MypageResponse;

public interface UserService {
    void deleteAccount();
    void logout();
    void modifyNickname(String nickname);
    MypageResponse getMyPage(String email);
}
