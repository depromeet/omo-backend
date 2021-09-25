package com.depromeet.omobackend.service.user;

import com.depromeet.omobackend.dto.user.UserDto;

public interface UserService {
    Long saveUser(UserDto requestDto);
}
