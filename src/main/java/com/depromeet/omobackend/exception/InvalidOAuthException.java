package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class InvalidOAuthException extends OmoException {
    public InvalidOAuthException() { super(401, "Invalid OAuth."); }
}
