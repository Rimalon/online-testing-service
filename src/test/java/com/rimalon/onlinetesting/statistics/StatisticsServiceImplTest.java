package com.rimalon.onlinetesting.statistics;

import com.rimalon.onlinetesting.BaseTest;
import com.rimalon.onlinetesting.datamodel.dto.QuestionJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.interfaces.AnswersService;
import com.rimalon.onlinetesting.interfaces.QuestionsService;
import com.rimalon.onlinetesting.interfaces.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class StatisticsServiceImplTest extends BaseTest {
    @Autowired
    QuestionsService questionsService;
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    AnswersService answersService;
    private int testId;
    private final String title = "?";
    private final String correctAnswer = "correct";
    private final String incorrectAnswer = "incorrect";


    @Before
    public void setup() {
        super.setup();
        RequestResultJSON<TestJSON> createTestResult = questionsService.createTest(userId1);
        testId = createTestResult.getResult().getTestId();
        questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, title, correctAnswer);
        questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, title, correctAnswer);
        QuestionJSON[] questions = questionsService.getTest(testId).getResult().getQuestions();
        answersService.addAnswer(userId1, questions[0].getId(), correctAnswer);
        answersService.addAnswer(userId1, questions[1].getId(), correctAnswer);
        answersService.addAnswer(userId2, questions[0].getId(), correctAnswer);
        answersService.addAnswer(userId2, questions[1].getId(), incorrectAnswer);
        answersService.addAnswer(userId3, questions[0].getId(), incorrectAnswer);
        answersService.addAnswer(userId3, questions[1].getId(), incorrectAnswer);


    }

    @Test
    public void getStatisticsTest() {
        RequestResultJSON<Integer> intResult = statisticsService.getTotalUsersAmount();
        assertEquals(1, intResult.getSuccess());
        assertEquals((Integer) 4, intResult.getResult());
        intResult = statisticsService.getUsersTestedAmount(testId);
        assertEquals(1, intResult.getSuccess());
        assertEquals((Integer) 3, intResult.getResult());
        RequestResultJSON<Long> longResult = statisticsService.getUsersAnsweredAllTestingQuestionsCorrectly(testId);
        assertEquals(1, longResult.getSuccess());
        assertEquals((Long) 1L, longResult.getResult());
        RequestResultJSON<Double> doubleResult = statisticsService.getUserPercentageOfCorrectAnswers(userId1, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(100.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUserPercentageOfCorrectAnswers(userId2, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(50.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUserPercentageOfCorrectAnswers(userId3, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(0.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUsersPercentageOfBetterThanUser(userId1, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(0.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUsersPercentageOfBetterThanUser(userId2, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(50.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUsersPercentageOfBetterThanUser(userId3, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(100.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUsersPercentageOfWorseThanUser(userId1, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(100.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUsersPercentageOfWorseThanUser(userId2, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(50.0, doubleResult.getResult(), 0.0);
        doubleResult = statisticsService.getUsersPercentageOfWorseThanUser(userId3, testId);
        assertEquals(1, doubleResult.getSuccess());
        assertEquals(0.0, doubleResult.getResult(), 0.0);
    }


}
