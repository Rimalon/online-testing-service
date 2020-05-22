package com.rimalon.onlinetesting.datamodel.enums;

import lombok.Getter;

public enum APIError {
    INTERNALL_ERROR("Internal error"),
    USER_ALREADY_REGISTERED("User with this name is already registered"),
    USER_ALREADY_LOGGED_IN("User is already logged in"),
    INCORRECT_USERNAME_OR_PASSWORD("Incorrect password"),
    USER_ALREADY_LOGGED_OUT("User is already logged out"),
    PASSWORD_MISMATCH("Password mismatch"),
    NO_ANSWERS_FOUND("No answers found for user"),
    USER_NOT_LOGGED_IN("User is not logged in");

    @Getter
    private final String message;

    APIError(String message) {
        this.message = message;
    }
}
