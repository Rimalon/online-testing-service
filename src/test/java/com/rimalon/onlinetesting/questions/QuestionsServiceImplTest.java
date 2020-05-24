package com.rimalon.onlinetesting.questions;

import com.rimalon.onlinetesting.BaseTest;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.interfaces.QuestionsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class QuestionsServiceImplTest extends BaseTest {
    @Autowired
    QuestionsService questionsService;

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void createTestWithQuestionsTest() {
        RequestResultJSON<TestJSON> createTestResult = questionsService.createTest(userId1);
        assertEquals(1, createTestResult.getSuccess());
        int testId = createTestResult.getResult().getTestId();
        RequestResultJSON<String> addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, "?", "correct");
        assertEquals(1, addQuestionResult.getSuccess());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, "?", "correct");
        assertEquals(1, addQuestionResult.getSuccess());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, "?", "correct");
        assertEquals(1, addQuestionResult.getSuccess());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, "?", "correct", "2", "3", "4");
        assertEquals(0, addQuestionResult.getSuccess());
        assertEquals(APIError.CANNOT_ADD_QUESTION.getMessage(), addQuestionResult.getError());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", "correct");
        assertEquals(0, addQuestionResult.getSuccess());
        assertEquals(APIError.CANNOT_ADD_QUESTION.getMessage(), addQuestionResult.getError());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", "correct", "2", "3", "4");
        assertEquals(1, addQuestionResult.getSuccess());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", "correct", "2", "3", "4");
        assertEquals(1, addQuestionResult.getSuccess());
        addQuestionResult = questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", "correct", "2", "3", "4");
        assertEquals(0, addQuestionResult.getSuccess());
        assertEquals(APIError.ONLY_FIVE_QUESTIONS_IN_ONE_TEST.getMessage(), addQuestionResult.getError());
        RequestResultJSON<TestJSON> getTestResult = questionsService.getTest(1);
        assertEquals(5, getTestResult.getResult().getQuestions().length);
    }


}
