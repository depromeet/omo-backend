package com.depromeet.omobackend.service.auth;

import com.depromeet.omobackend.exception.UserNotAuthenticatedException;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationUtil authenticationUtil;

    @Override
    public void logout() {
        try{
            refreshTokenRepository.findById(authenticationUtil.getUserEmail())
                    .ifPresent(refreshTokenRepository::delete);
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserNotAuthenticatedException();
        }
    }

}
