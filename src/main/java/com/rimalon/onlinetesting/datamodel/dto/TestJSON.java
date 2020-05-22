package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestJSON {
    @JsonProperty
    int testId;
    @JsonProperty
    QuestionJSON[] questions;
}
