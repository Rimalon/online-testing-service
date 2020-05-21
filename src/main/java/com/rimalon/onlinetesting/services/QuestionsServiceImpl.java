package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.entities.Question;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import com.rimalon.onlinetesting.interfaces.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionsServiceImpl implements QuestionService {
    QueryHelper queryHelper;


    @Autowired
    public QuestionsServiceImpl(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    @Override
    public RequestResultJSON<String> addQuestion(Integer userId, Integer testId, QuestionType type, String question, String answer) {
        boolean result = queryHelper.save(Question.class, "(testId, type, question, answer, authorId)",
                new Object[]{testId, type.getValue(), question, answer, userId});
        if (!result){
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            return new RequestResultJSON<>(true, "GOOD", null);
        }
    }
}
