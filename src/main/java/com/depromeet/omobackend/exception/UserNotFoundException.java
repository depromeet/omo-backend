package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class UserNotFoundException extends OmoException {
    public UserNotFoundException() {
        super(404, "User not found exception.");
    }
}
