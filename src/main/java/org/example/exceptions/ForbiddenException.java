package org.example.exceptions;

import org.example.exceptions.errors.ErrorCode;

public class ForbiddenException extends ErrorCodeException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ForbiddenException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

}
