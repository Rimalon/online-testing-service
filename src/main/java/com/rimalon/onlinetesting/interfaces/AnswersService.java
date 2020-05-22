package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;

public interface AnswersService {
    RequestResultJSON<String> addAnswer(Integer userId, Integer questionId, String answer);

    RequestResultJSON<AnswersJSON> getAnswers(Integer userId);
}
