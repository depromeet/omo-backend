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
            System.out.println(authenticationUtil.getUserEmail());
            refreshTokenRepository.deleteById(authenticationUtil.getUserEmail());
        } catch(Exception e) {
            e.printStackTrace();
            throw new UserNotAuthenticatedException();
        }
    }

}
