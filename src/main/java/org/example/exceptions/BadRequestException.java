package org.example.exceptions;


import org.example.exceptions.errors.ErrorCode;

public class BadRequestException extends ErrorCodeException {

    public BadRequestException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

}
