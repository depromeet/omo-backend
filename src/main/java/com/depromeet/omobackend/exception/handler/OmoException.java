package com.depromeet.omobackend.exception.handler;

import lombok.Getter;

@Getter
public class OmoException extends RuntimeException {

    private final int status;
    private final String message;

    public OmoException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

}
