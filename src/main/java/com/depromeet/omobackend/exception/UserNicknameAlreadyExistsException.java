package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class UserNicknameAlreadyExistsException extends OmoException {
    public UserNicknameAlreadyExistsException() {
        super(409, "Nickname is already exists.");
    }
}
