package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.repository.refresh.RefreshTokenRepository;
import com.depromeet.omobackend.repository.user.UserRepository;
import com.depromeet.omobackend.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationUtil authenticationUtil;

    @Override
    public void deleteAccount() {
        User user = authenticationUtil.getUser();
        userRepository.delete(user);
    }

    @Override
    public void logout() {
        refreshTokenRepository.deleteById(authenticationUtil.getUserEmail());
    }

    @Override
    public void modifyNickname(String nickname) {
        User user = authenticationUtil.getUser();
        user.modifyNickname(nickname);
    }

}
