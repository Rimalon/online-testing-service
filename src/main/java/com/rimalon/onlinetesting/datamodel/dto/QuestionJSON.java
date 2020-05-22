package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.entities.Question;

public class QuestionJSON {
    @JsonProperty
    Integer id;
    @JsonProperty
    String title;

    @JsonCreator
    public QuestionJSON(Question question){
        this.id  = question.getId();
        this.title = question.getTitle();
    }
}
