package org.example.configurations.handlers;


import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ErrorCodeException;
import org.example.exceptions.errors.ErrorCode;
import org.example.exceptions.errors.ErrorMessage;
import org.springframework.http.HttpStatus;

@Slf4j
public class BaseExceptionHandler {

    ErrorMessage buildErrorMessageWithErrorCodeException(ErrorCodeException exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(exception.getErrorCode().getCode());
        errorMessage.setDescription(exception.getMessage());
        return errorMessage;
    }

    ErrorMessage buildErrorMessageWithErrorCode(ErrorCode errorCode) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(errorCode.getCode());
        errorMessage.setDescription(errorCode.getDescription());
        return errorMessage;
    }

    ErrorMessage buildErrorMessageWithStatusAndException(HttpStatus status, Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(status.getReasonPhrase());
        errorMessage.setDescription(exception.getMessage());
        return errorMessage;
    }

}
