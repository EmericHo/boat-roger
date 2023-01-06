package org.example.exceptions;

import org.example.exceptions.errors.ErrorCode;

public class NotFoundException extends ErrorCodeException {

    public NotFoundException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

}
