package org.example.exceptions.errors;

public enum AuthenticationError implements ErrorCode {

    INVALID_CREDENTIALS("AU0001", "Invalid authentication");

    private final String code;
    private final String description;

    AuthenticationError(String code, String description) {
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

    @Override
    public String toString() {
        return "AuthenticationBusinessError{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
