package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.entities.Answer;

public class AnswerJSON {
    @JsonProperty
    String answer;
    @JsonProperty
    Boolean isCorrect;

    @JsonCreator
    public AnswerJSON(Answer answer) {
        this.answer = answer.getAnswer();
        this.isCorrect = answer.getIsCorrect();
    }
}
