package com.rimalon.onlinetesting.datamodel.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Answer extends BaseEntity {
    Integer id;
    Integer userId;
    Integer questionId;
    String answer;
    Boolean isCorrect;
}
