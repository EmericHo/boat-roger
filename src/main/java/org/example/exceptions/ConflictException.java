package org.example.exceptions;

import org.example.exceptions.errors.ErrorCode;

public class ConflictException extends ErrorCodeException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ConflictException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

}
