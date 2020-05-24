package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.interfaces.QuestionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/quest-api")
public class QuestionsController extends BaseController {
    private QuestionsService questionsService;


    @Autowired
    public QuestionsController(QuestionsService questionsService, CacheHelper cacheHelper) {
        super(log, cacheHelper);
        this.questionsService = questionsService;
    }

    @PostMapping("/createTest")
    public RequestResultJSON<TestJSON> createTest(@RequestParam String cookie) {
        return executeByLoggedUser(cookie, "addTest", (userId) -> questionsService.createTest(userId));

    }

    @GetMapping("/getTest")
    public RequestResultJSON<TestJSON> getTest(@RequestParam String cookie,
                                               @RequestParam int testId) {
        return executeByLoggedUser(cookie, "getTest",
                String.format("testId=%s", testId),
                (userId) -> questionsService.getTest(userId, testId));
    }

    @PostMapping("/addFreeEntryQuestion")
    public RequestResultJSON<String> addFreeEntryQuestion(@RequestParam String cookie,
                                                          @RequestParam int testId,
                                                          @RequestParam String title,
                                                          @RequestParam String correctAnswer) {
        return executeByLoggedUser(cookie, "addChoiceOfAnswerQuestion",
                String.format("testId=%s, title=%s, answer=%s", testId, title, correctAnswer),
                (userId) -> questionsService.addQuestion(userId, testId, QuestionType.freeEntry, title, correctAnswer));
    }

    @PostMapping("/addChoiceOfAnswerQuestion")
    public RequestResultJSON<String> addChoiceOfAnswerQuestion(@RequestParam String cookie,
                                                               @RequestParam int testId,
                                                               @RequestParam String title,
                                                               @RequestParam String correctAnswer,
                                                               @RequestParam String secondOption,
                                                               @RequestParam String thirdOption,
                                                               @RequestParam String fourthOption) {
        return executeByLoggedUser(cookie, "addFreeEntryQuestion",
                String.format("testId=%s, title=%s,  correctAnswer=%s, secondOption=%s, thirdOption=%s, fourthOption=%s",
                        testId, title, correctAnswer, secondOption, thirdOption, fourthOption),
                (userId) -> questionsService.addQuestion(userId, testId, QuestionType.choiceOfAnswer, title, correctAnswer, secondOption, thirdOption, fourthOption));
    }
}
