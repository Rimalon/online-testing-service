package com.rimalon.onlinetesting.datamodel.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Question extends BaseEntity {
    Integer id;
    Integer testId;
    QuestionType type;
    String title;
    Integer authorId;
    String correctAnswer;
    String secondOption;
    String thirdOption;
    String fourthOption;
}
