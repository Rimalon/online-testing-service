package com.rimalon.onlinetesting.datamodel.enums;

import lombok.Getter;

public enum QuestionType {
    choiceOfAnswer(0),
    freeEntry(1);


    @Getter
    private final int value;

    QuestionType(Integer value) {
        this.value = value;
    }
}
