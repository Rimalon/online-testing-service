package com.rimalon.onlinetesting.answers;

import com.rimalon.onlinetesting.BaseTest;
import com.rimalon.onlinetesting.datamodel.dto.AnswerJSON;
import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.QuestionJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.interfaces.AnswersService;
import com.rimalon.onlinetesting.interfaces.QuestionsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AnswerServiceImplTest extends BaseTest {
    private int testId;

    private final String correctAnswer = "correct";
    private final String secondOption = "2";
    private final String thirdOption = "3";
    private final String fourthOprion = "4";
    private final String incorrectOption = "incorrect";

    @Autowired
    QuestionsService questionsService;
    @Autowired
    AnswersService answersService;

    @Before
    public void setup() {
        super.setup();
        RequestResultJSON<TestJSON> createTestResult = questionsService.createTest(userId1);
        testId = createTestResult.getResult().getTestId();
        questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, "?", correctAnswer);
        questionsService.addQuestion(userId1, testId, QuestionType.freeEntry, "?", correctAnswer);
        questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", correctAnswer, secondOption, thirdOption, fourthOprion);
        questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", correctAnswer, secondOption, thirdOption, fourthOprion);
        questionsService.addQuestion(userId1, testId, QuestionType.choiceOfAnswer, "?", correctAnswer, secondOption, thirdOption, fourthOprion);
    }

    @Test
    public void addAnswersTest() {
        RequestResultJSON<TestJSON> getTestResult = questionsService.getTest(testId);
        QuestionJSON[] questions = getTestResult.getResult().getQuestions();
        RequestResultJSON<String> addAnswerResult = answersService.addAnswer(userId1, questions[0].getId(), correctAnswer);
        assertEquals(1, addAnswerResult.getSuccess());
        addAnswerResult = answersService.addAnswer(userId1, questions[0].getId(), correctAnswer);
        assertEquals(0, addAnswerResult.getSuccess());
        assertEquals(APIError.CANNOT_ADD_ANSWER.getMessage(), addAnswerResult.getError());
        addAnswerResult = answersService.addAnswer(userId1, questions[1].getId(), secondOption);
        assertEquals(1, addAnswerResult.getSuccess());
        addAnswerResult = answersService.addAnswer(userId1, questions[2].getId(), incorrectOption);
        assertEquals(0, addAnswerResult.getSuccess());
        assertEquals(APIError.CANNOT_ADD_ANSWER.getMessage(), addAnswerResult.getError());
        addAnswerResult = answersService.addAnswer(userId1, questions[2].getId(), thirdOption);
        assertEquals(1, addAnswerResult.getSuccess());
        addAnswerResult = answersService.addAnswer(userId1, questions[2].getId(), correctAnswer);
        assertEquals(0, addAnswerResult.getSuccess());
        assertEquals(APIError.CANNOT_ADD_ANSWER.getMessage(), addAnswerResult.getError());
        addAnswerResult = answersService.addAnswer(userId1, questions[3].getId(), correctAnswer);
        assertEquals(1, addAnswerResult.getSuccess());
        addAnswerResult = answersService.addAnswer(userId1, questions[4].getId(), correctAnswer);
        assertEquals(1, addAnswerResult.getSuccess());
        RequestResultJSON<AnswersJSON> getAnswersResult = answersService.getAnswers(userId1, testId);
        assertEquals(1, getAnswersResult.getSuccess());
        List<AnswerJSON> answers = Arrays.stream(getAnswersResult.getResult().getAnswers().get(String.format("Test#%d", testId))).collect(Collectors.toList());
        assertEquals(5, answers.size());
        assertEquals(3, answers.stream().filter(AnswerJSON::getIsCorrect).count());

    }
}
