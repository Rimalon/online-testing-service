package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.entities.Question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChoiceOfAnswerQuestionJSON extends QuestionJSON {
    @JsonProperty
    String firstOption;
    @JsonProperty
    String secondOption;
    @JsonProperty
    String thirdOption;
    @JsonProperty
    String fourthOption;

    @JsonCreator
    public ChoiceOfAnswerQuestionJSON(Question question) {
        super(question);

        List<String> optionsList = Arrays.asList(question.getCorrectAnswer(), question.getSecondOption(), question.getThirdOption(), question.getFourthOption());
        Collections.shuffle(optionsList);
        this.firstOption = optionsList.get(0);
        this.secondOption = optionsList.get(1);
        this.thirdOption = optionsList.get(2);
        this.fourthOption = optionsList.get(3);
    }

}
