package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class UserNotAuthenticatedException extends OmoException {
    public UserNotAuthenticatedException() {
        super(401, "User is not authenticated.");
    }
}
