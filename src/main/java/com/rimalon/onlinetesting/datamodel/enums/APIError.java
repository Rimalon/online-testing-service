package com.rimalon.onlinetesting.datamodel.enums;

import lombok.Getter;

public enum APIError {
    INTERNALL_ERROR("Internal error"),
    WRONG_COOKIE("Wrong cookie"),
    USER_ALREADY_REGISTERED("User with this name is already registered"),
    USER_ALREADY_LOGGED_IN("User is already logged in"),
    INCORRECT_USERNAME_OR_PASSWORD("Incorrect password"),
    PASSWORD_MISMATCH("Password mismatch"),
    NO_ANSWERS_FOUND("No answers found for user"),
    USER_NOT_LOGGED_IN("User is not logged in"),
    ONLY_FIVE_QUESTIONS_IN_ONE_TEST("You cannot add more than 5 questions to the test"),
    USER_DONT_HAVE_ANSWERS("User does not have answers");

    @Getter
    private final String message;

    APIError(String message) {
        this.message = message;
    }
}
