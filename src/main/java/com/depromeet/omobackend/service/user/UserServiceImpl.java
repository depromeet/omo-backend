package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.user.UserDto;
import com.depromeet.omobackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long saveUser(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getId();
    }
}
