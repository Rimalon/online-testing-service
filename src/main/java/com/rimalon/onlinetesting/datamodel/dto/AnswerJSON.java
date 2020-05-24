package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.entities.Answer;
import lombok.Getter;

public class AnswerJSON {
    @JsonProperty
    String answer;
    @JsonProperty
    Integer questionId;
    @JsonProperty
    @Getter
    Boolean isCorrect;

    @JsonCreator
    public AnswerJSON(Answer answer) {
        this.answer = answer.getAnswer();
        this.questionId = answer.getQuestionId();
        this.isCorrect = answer.getIsCorrect();
    }
}
