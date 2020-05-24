package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.ids.UserId;

public interface AnswersService {
    RequestResultJSON<String> addAnswer(UserId userId, Integer questionId, String answer);

    RequestResultJSON<AnswersJSON> getAnswers(UserId userId, Integer testId);

    RequestResultJSON<AnswersJSON> getAnswers(UserId userId);
}
