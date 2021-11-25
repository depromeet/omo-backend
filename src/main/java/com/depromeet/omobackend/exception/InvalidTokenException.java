package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class InvalidTokenException extends OmoException {
    public InvalidTokenException() {
        super(400, "Invalid token.");
    }
}
