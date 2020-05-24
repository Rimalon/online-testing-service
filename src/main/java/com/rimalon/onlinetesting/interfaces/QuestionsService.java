package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.datamodel.ids.UserId;

public interface QuestionsService {
    RequestResultJSON<TestJSON> createTest(UserId userId);

    RequestResultJSON<TestJSON> getTest(UserId userId, Integer testId);

    RequestResultJSON<String> addQuestion(UserId userId, Integer testId, QuestionType type, String title, String correctAnswer);

    RequestResultJSON<String> addQuestion(UserId userId, Integer testId, QuestionType type, String title,
                                          String correctAnswer, String secondOption, String thirdOption, String fourthOption);
}
