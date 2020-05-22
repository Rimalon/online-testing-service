package com.rimalon.onlinetesting.services;

import com.rimalon.onlinetesting.datamodel.dto.AnswerJSON;
import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.entities.Answer;
import com.rimalon.onlinetesting.datamodel.entities.Question;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import com.rimalon.onlinetesting.interfaces.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswersServiceImpl implements AnswersService {
    private QueryHelper queryHelper;

    @Autowired
    public AnswersServiceImpl(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    @Override
    public RequestResultJSON<String> addAnswer(Integer userId, Integer questionId, String answer) {
        Question question = queryHelper.getById(Question.class, questionId);
        boolean result = queryHelper.save(Answer.class, "(userId, questionId, answer, isCorrect)",
                new Object[]{userId, questionId, answer, question.getCorrectAnswer().equalsIgnoreCase(answer)});
        if (!result) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            return new RequestResultJSON<>(true, "Answer correctly  added", null);
        }
    }

    @Override
    public RequestResultJSON<AnswersJSON> getAnswers(Integer userId) {
        List<Answer> answers = queryHelper.getListObjectsByWhereClause(Answer.class, "userId = ?", new Object[]{userId});
        if (answers == null) {
            return RequestResultJSON.errorResult(APIError.NO_ANSWERS_FOUND);
        } else {
            return new RequestResultJSON<>(true, new AnswersJSON(answers.stream().map(AnswerJSON::new).toArray(AnswerJSON[]::new)), null);
        }
    }
}
