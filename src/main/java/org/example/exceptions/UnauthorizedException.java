package org.example.exceptions;

import org.example.exceptions.errors.ErrorCode;

public class UnauthorizedException extends ErrorCodeException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnauthorizedException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

}
