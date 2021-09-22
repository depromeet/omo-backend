package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class TokenTypeNotRefreshException extends OmoException {
    public TokenTypeNotRefreshException() {
        super(400, "Token type is not refresh.");
    }
}
