package com.rimalon.onlinetesting.datamodel.enums;

import lombok.Getter;

public enum APIError {
    INTERNAL_ERROR("Internal error"),
    WRONG_COOKIE("Wrong cookie"),
    USER_ALREADY_REGISTERED("User with this name is already registered"),
    USER_ALREADY_LOGGED_IN("User is already logged in"),
    INCORRECT_USERNAME_OR_PASSWORD("Incorrect password"),
    PASSWORD_MISMATCH("Password mismatch"),
    NO_ANSWERS_FOUND("No answers found for user"),
    USER_NOT_LOGGED_IN("User is not logged in"),
    ONLY_FIVE_QUESTIONS_IN_ONE_TEST("You cannot add more than 5 questions to the test"),
    QUESTIONS_NOT_FOUND("No questions were found for test"),
    ANSWERS_NOT_FOUND_FOR_TEST("No answers were found for test"),
    ANSWERS_NOT_FOUND_FOR_USER("No answers were found for user"),
    USER_DONT_HAVE_ANSWERS("User does not have answers"),
    CANNOT_ADD_QUESTION("You cannot add a question with such parameters"),
    CANNOT_CREATE_TEST("The test was not created"),
    CANNOT_ADD_ANSWER("You cannot add a answer with such parameters"),
    CANNOT_CREATE_USER("You cannot create a user with such parameters"),
    USERS_NOT_FOUND("Users not found");

    @Getter
    private final String message;

    APIError(String message) {
        this.message = message;
    }
}
