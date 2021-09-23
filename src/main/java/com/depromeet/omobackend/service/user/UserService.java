package com.depromeet.omobackend.service.user;

public interface UserService {
    void deleteAccount();
    void logout();
    void modifyNickname(String nickname);
}
