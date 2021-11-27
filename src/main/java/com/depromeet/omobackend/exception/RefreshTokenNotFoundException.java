package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class RefreshTokenNotFoundException extends OmoException {
    public RefreshTokenNotFoundException() {
        super(404, "Refresh Token Not Found.");
    }
}
