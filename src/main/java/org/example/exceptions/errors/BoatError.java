package org.example.exceptions.errors;

public enum BoatError implements ErrorCode {

    NOT_FOUND("BO0001", "The boat with id : %s is not found"),
    BAD_REQUEST("BO0001", "The boat request missing this param: %s");

    private final String code;
    private final String description;

    BoatError(String code, String description) {
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

