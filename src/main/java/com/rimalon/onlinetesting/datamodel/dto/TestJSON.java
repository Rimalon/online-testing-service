package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TestJSON {
    @JsonProperty
    @Getter
    int testId;
    @JsonProperty
    @Getter
    QuestionJSON[] questions;
}
