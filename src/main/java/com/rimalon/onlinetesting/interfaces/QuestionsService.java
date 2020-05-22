package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.entities.Test;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;

public interface QuestionsService {
    RequestResultJSON<Test> addTest(Integer userId);

    RequestResultJSON<TestJSON> getTest(Integer userId, Integer testId);

    RequestResultJSON<String> addQuestion(Integer userId, Integer testId, QuestionType type, String title, String correctAnswer);

    RequestResultJSON<String> addQuestion(Integer userId, Integer testId, QuestionType type, String title,
                                          String correctAnswer, String secondOption, String thirdOption, String fourthOption);
}
