package com.rimalon.onlinetesting.services;

import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.entities.Answer;
import com.rimalon.onlinetesting.datamodel.entities.Question;
import com.rimalon.onlinetesting.datamodel.entities.Test;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import com.rimalon.onlinetesting.interfaces.AnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswersServiceImpl implements AnswersService {
    private QueryHelper queryHelper;

    @Autowired
    public AnswersServiceImpl(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    @Override
    public RequestResultJSON<String> addAnswer(UserId userId, Integer questionId, String answer) {
        Question question = queryHelper.getById(Question.class, questionId);
        if (question == null) {
            return RequestResultJSON.errorResult(APIError.CANNOT_ADD_ANSWER);
        }
        boolean result = queryHelper.save(Answer.class, "userId, questionId, answer, isCorrect",
                new Object[]{userId, questionId, answer, question.getCorrectAnswer().equalsIgnoreCase(answer)});
        if (!result) {
            return RequestResultJSON.errorResult(APIError.CANNOT_ADD_ANSWER);
        } else {
            return new RequestResultJSON<>(true, "Answer correctly  added", null);
        }
    }

    @Override
    public RequestResultJSON<AnswersJSON> getAnswers(UserId userId, Integer testId) {
        List<Question> questionsList = queryHelper.getListObjectsByJoinClause(Question.class, Test.class, "Test.id = Question.testId AND Test.id = ?", new Object[]{testId});
        if (questionsList == null) {
            return RequestResultJSON.errorResult(APIError.QUESTIONS_NOT_FOUND);
        }
        List<Answer> answers = queryHelper.getListObjectsByWhereClause(Answer.class,
                String.format("userId = ? AND questionId IN (%s)", questionsList.stream().map(q -> q.getId().toString()).collect(Collectors.joining(","))),
                new Object[]{userId});
        if (answers == null) {
            return RequestResultJSON.errorResult(APIError.NO_ANSWERS_FOUND);
        } else {
            Map<String, List<Answer>> answersMap = new HashMap<>();
            answersMap.put(String.format("Test#%d", testId), answers);
            return new RequestResultJSON<>(true, new AnswersJSON(answersMap), null);
        }
    }


    @Override
    public RequestResultJSON<AnswersJSON> getAnswers(UserId userId) {
        List<Map<String, Object>> testIdAndQuestIdList = queryHelper.getListOfMapsByJoinClause("Question.id as qId, Test.id as tId",
                Question.class, Test.class, "Test.id = Question.testId");
        if (testIdAndQuestIdList == null) {
            return RequestResultJSON.errorResult(APIError.QUESTIONS_NOT_FOUND);
        }
        Map<Integer, Integer> questionsTestMap = new HashMap<>();
        for (Map<String, Object> row : testIdAndQuestIdList) {
            questionsTestMap.put((Integer) row.get("qId"), (Integer) row.get("tId"));
        }
        List<Answer> answers = queryHelper.getListObjectsByWhereClause(Answer.class, "userId = ?", new Object[]{userId});
        if (answers == null) {
            return RequestResultJSON.errorResult(APIError.NO_ANSWERS_FOUND);
        } else {
            Map<String, List<Answer>> answersMap = new HashMap<>();
            for (Answer answer : answers) {
                String testName = String.format("Test#%d", questionsTestMap.get(answer.getQuestionId()));
                answersMap.computeIfAbsent(testName, k -> new ArrayList<>());
                answersMap.get(testName).add(answer);
            }
            return new RequestResultJSON<>(true, new AnswersJSON(answersMap), null);
        }
    }
}
