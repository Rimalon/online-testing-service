package com.rimalon.onlinetesting.datamodel.entities;

import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Question extends BaseEntity {
    Integer id;
    Integer testId;
    QuestionType type;
    String title;
    UserId authorId;
    String correctAnswer;
    String secondOption;
    String thirdOption;
    String fourthOption;
}
