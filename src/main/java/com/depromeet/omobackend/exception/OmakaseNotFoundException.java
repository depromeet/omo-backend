package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class OmakaseNotFoundException extends OmoException {
    public OmakaseNotFoundException() {
        super(404, "Omakase is not found.");
    }
}
