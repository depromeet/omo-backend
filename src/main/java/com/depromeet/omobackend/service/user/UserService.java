package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.response.ProfileResponse;

public interface UserService {
    void deleteAccount();
    void logout();
    void modifyNickname(String nickname);
    ProfileResponse getProfile(String email);
}
