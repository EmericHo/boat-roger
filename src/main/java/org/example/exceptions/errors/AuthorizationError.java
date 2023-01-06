package org.example.exceptions.errors;

public enum AuthorizationError implements ErrorCode {

    NOT_AUTHORIZED("A0001", "User not authorized."),
    JWT_INVALID("A0003", "JWT invalid"),
    JWT_EXPIRED("A0004", "JWT expired");

    private final String code;
    private final String description;

    AuthorizationError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
