package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class AlreadyCertificatedOmakaseException extends OmoException {
    public AlreadyCertificatedOmakaseException() {
        super(409, "User already certificated this omakase.");
    }
}
