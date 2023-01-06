package org.example.exceptions.errors;

public enum AccountError implements ErrorCode {

    INVALID_REQUEST("AC0001", "Invalid request : %s "),
    CONFLICT_USERNAME("AC0002", "Conflict username: %s already exists");

    private final String code;
    private final String description;

    AccountError(String code, String description) {
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
        return "AccountBusinessError{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
