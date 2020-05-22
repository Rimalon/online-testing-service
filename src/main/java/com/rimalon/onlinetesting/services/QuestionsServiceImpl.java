package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.ChoiceOfAnswerQuestionJSON;
import com.rimalon.onlinetesting.datamodel.dto.QuestionJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.entities.Question;
import com.rimalon.onlinetesting.datamodel.entities.Test;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
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
    public synchronized RequestResultJSON<Test> addTest(Integer userId) {
        boolean saveResult = queryHelper.save(Test.class, "(authorId)", new Object[]{userId});
        if (!saveResult) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            Test result = queryHelper.getObjectWithMaxId(Test.class);
            return new RequestResultJSON<>(true, result, null);
        }
    }

    @Override
    public RequestResultJSON<TestJSON> getTest(Integer userId, Integer testId) {
        List<Question> questionList = queryHelper.getListObjectsByJoinClause(Question.class, Test.class, String.format("Test.id = Question.testId AND Test.id = %d", testId));
        if (questionList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
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
    public RequestResultJSON<String> addQuestion(Integer userId, Integer testId, QuestionType type, String title,
                                                 String correctAnswer, String secondOption, String thirdOption, String fourthOption) {
        boolean result = queryHelper.save(Question.class, "(testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption)",
                new Object[]{testId, type.getValue(), title, userId, correctAnswer, secondOption, thirdOption, fourthOption});
        if (!result) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            return new RequestResultJSON<>(true, "Question correctly  added", null);
        }
    }

    @Override
    public RequestResultJSON<String> addQuestion(Integer userId, Integer testId, QuestionType type, String title, String correctAnswer) {
        boolean result = queryHelper.save(Question.class, "(testId, type, title, authorId, correctAnswer)",
                new Object[]{testId, type.getValue(), title, userId, correctAnswer});
        if (!result) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            return new RequestResultJSON<>(true, "Question correctly  added", null);
        }
    }
}
