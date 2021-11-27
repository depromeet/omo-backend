package com.depromeet.omobackend.service.auth;

import com.depromeet.omobackend.dto.response.TokenResponse;

public interface AuthService {
    void logout();
    TokenResponse tokenRefresh(String xRefreshToken);
}
