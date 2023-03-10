package org.example.exceptions;


import org.example.exceptions.errors.ErrorCode;

public class InternalServerErrorException extends ErrorCodeException {

    public InternalServerErrorException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InternalServerErrorException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    public InternalServerErrorException(ErrorCode errorCode, Throwable cause, Object... args) {
        super(errorCode, cause, args);
    }

}
