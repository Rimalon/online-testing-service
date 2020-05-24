package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.ChoiceOfAnswerQuestionJSON;
import com.rimalon.onlinetesting.datamodel.dto.QuestionJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.entities.Question;
import com.rimalon.onlinetesting.datamodel.entities.Test;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import com.rimalon.onlinetesting.interfaces.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {
    private QueryHelper queryHelper;

    @Autowired
    public QuestionsServiceImpl(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    @Override
    public synchronized RequestResultJSON<TestJSON> createTest(UserId userId) {
        boolean saveResult = queryHelper.save(Test.class, "authorId", new Object[]{userId});
        if (!saveResult) {
            return RequestResultJSON.errorResult(APIError.CANNOT_CREATE_TEST);
        } else {
            Test result = queryHelper.getObjectWithMaxId(Test.class);
            return new RequestResultJSON<>(true, new TestJSON(result.getId(), null), null);
        }
    }

    @Override
    public RequestResultJSON<TestJSON> getTest(Integer testId) {
        List<Question> questionList = queryHelper.getListObjectsByJoinClause(Question.class, Test.class, "Test.id = Question.testId AND Test.id = ?", new Object[]{testId});
        if (questionList == null) {
            return RequestResultJSON.errorResult(APIError.QUESTIONS_NOT_FOUND);
        } else {
            return new RequestResultJSON<>(true, new TestJSON(testId, questionList.stream().map(q -> {
                switch (q.getType()) {
                    case choiceOfAnswer:
                        return new ChoiceOfAnswerQuestionJSON(q);
                    case freeEntry:
                        return new QuestionJSON(q);
                }
                return null;
            }).toArray(QuestionJSON[]::new)), null);
        }
    }

    @Override
    public RequestResultJSON<String> addQuestion(UserId userId, Integer testId, QuestionType type, String title,
                                                 String correctAnswer, String secondOption, String thirdOption, String fourthOption) {
        List<Question> questionListByTestId = queryHelper.getListObjectsByWhereClause(Question.class, "testId = ?", new Object[]{testId});
        if (questionListByTestId.size() >= 5) {
            return RequestResultJSON.errorResult(APIError.ONLY_FIVE_QUESTIONS_IN_ONE_TEST);
        }
        boolean result = queryHelper.save(Question.class, "testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption",
                new Object[]{testId, type.getValue(), title, userId, correctAnswer, secondOption, thirdOption, fourthOption});
        if (!result) {
            return RequestResultJSON.errorResult(APIError.CANNOT_ADD_QUESTION);
        } else {
            return new RequestResultJSON<>(true, "Question correctly  added", null);
        }
    }

    @Override
    public RequestResultJSON<String> addQuestion(UserId userId, Integer testId, QuestionType type, String title, String correctAnswer) {
        List<Question> questionListByTestId = queryHelper.getListObjectsByWhereClause(Question.class, "testId = ?", new Object[]{testId});
        if (questionListByTestId.size() >= 5) {
            return RequestResultJSON.errorResult(APIError.ONLY_FIVE_QUESTIONS_IN_ONE_TEST);
        }
        boolean result = queryHelper.save(Question.class, "testId, type, title, authorId, correctAnswer",
                new Object[]{testId, type.getValue(), title, userId, correctAnswer});
        if (!result) {
            return RequestResultJSON.errorResult(APIError.CANNOT_ADD_QUESTION);
        } else {
            return new RequestResultJSON<>(true, "Question correctly  added", null);
        }
    }
}
