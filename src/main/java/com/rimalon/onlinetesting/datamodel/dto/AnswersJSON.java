package com.rimalon.onlinetesting.datamodel.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AnswersJSON {
    @JsonProperty
    AnswerJSON[] answers;
}
