package com.depromeet.omobackend.service.login;

import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationUtil authenticationUtil;

    @Override
    public void logout() {
        refreshTokenRepository.deleteById(authenticationUtil.getUserEmail());
    }
}
