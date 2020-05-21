package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;

public interface QuestionService {

    RequestResultJSON<String> addQuestion(Integer userId, Integer testId, QuestionType type,  String question, String answer);


    
}
