package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class ExpiredTokenException extends OmoException {
    public ExpiredTokenException() {
        super(401, "Token is expired");
    }
}
