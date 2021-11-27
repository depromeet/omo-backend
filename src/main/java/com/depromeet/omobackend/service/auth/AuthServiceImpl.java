package com.depromeet.omobackend.service.auth;

import com.depromeet.omobackend.domain.refreshtoken.RefreshToken;
import com.depromeet.omobackend.dto.response.TokenResponse;
import com.depromeet.omobackend.exception.InvalidTokenException;
import com.depromeet.omobackend.exception.RefreshTokenNotFoundException;
import com.depromeet.omobackend.exception.UserNotAuthenticatedException;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.security.jwt.JwtTokenProvider;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.exp.refresh}")
    private long refreshExp;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationUtil authenticationUtil;

    private final JwtTokenProvider jwtTokenProvider;

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

    @Override
    public TokenResponse tokenRefresh(String xRefreshToken) {
        if (!jwtTokenProvider.isRefreshToken(xRefreshToken)) {
            throw new InvalidTokenException();
        }

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(xRefreshToken)
                .map(r -> r.update(jwtTokenProvider.generateRefreshToken(r.getEmail()), refreshExp))
                .orElseThrow(RefreshTokenNotFoundException::new);

        return new TokenResponse(jwtTokenProvider.generateAccessToken(refreshToken.getEmail()), refreshToken.getRefreshToken());
    }


}
