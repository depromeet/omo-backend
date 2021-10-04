package com.depromeet.omobackend.util;

import com.depromeet.omobackend.domain.user.User;
import com.depromeet.omobackend.exception.UserNotAuthenticatedException;
import com.depromeet.omobackend.exception.UserNotFoundException;
import com.depromeet.omobackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationUtil {

    private final UserRepository userRepository;

    public String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        return authentication.getName();
    }

    public User getUser() {
        return userRepository.findByEmail(getUserEmail())
//                .filter(User::getIsActivated) // 해당 메소드 없어 오류가 발생하여 주석 처리
                .orElseThrow(UserNotFoundException::new);
    }

}
