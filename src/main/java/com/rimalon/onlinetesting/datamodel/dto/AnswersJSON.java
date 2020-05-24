package com.rimalon.onlinetesting.datamodel.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.entities.Answer;
import lombok.Getter;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnswersJSON {
    @JsonProperty
    @Getter
    private Map<String, AnswerJSON[]> answers;


    public AnswersJSON(Map<String, List<Answer>> answersMap) {
        this.answers = answersMap
                .entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().stream().map(AnswerJSON::new).toArray(AnswerJSON[]::new)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
