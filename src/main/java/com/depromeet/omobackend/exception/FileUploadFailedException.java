package com.depromeet.omobackend.exception;

import com.depromeet.omobackend.exception.handler.OmoException;

public class FileUploadFailedException extends OmoException {
    public FileUploadFailedException() {
        super(400, "File upload failed.");
    }
}
