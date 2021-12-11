package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class FileIsEmptyException extends OmoException {
    public FileIsEmptyException() {
        super(400, "File is empty.");
    }
}
