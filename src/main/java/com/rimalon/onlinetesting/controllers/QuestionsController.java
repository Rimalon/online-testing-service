package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.TestJSON;
import com.rimalon.onlinetesting.datamodel.entities.Test;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.interfaces.QuestionsService;
import com.rimalon.onlinetesting.interfaces.UserPermissionsService;
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
    private UserPermissionsService userPermissionsService;


    @Autowired
    public QuestionsController(QuestionsService questionsService, UserPermissionsService userPermissionsService) {
        super(log);
        this.questionsService = questionsService;
        this.userPermissionsService = userPermissionsService;
    }

    @PostMapping("/addTest")
    public RequestResultJSON<Test> addTest(@RequestParam Integer userId) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addTest called. userId=%s", userId),
                () -> questionsService.addTest(userId));

    }

    @GetMapping("/getTest")
    public RequestResultJSON<TestJSON> getTest(@RequestParam Integer userId,
                                               @RequestParam Integer testId) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addTest called. userId=%s, testId=%s", userId, testId),
                () -> questionsService.getTest(userId, testId));
    }

    @PostMapping("/addFreeEntryQuestion")
    public RequestResultJSON<String> addFreeEntryQuestion(@RequestParam Integer userId,
                                                          @RequestParam Integer testId,
                                                          @RequestParam String title,
                                                          @RequestParam String correctAnswer) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addChoiceOfAnswerQuestion called. userId=%s, testId=%s, title=%s, answer=%s", userId, testId, title, correctAnswer),
                () -> questionsService.addQuestion(userId, testId, QuestionType.freeEntry, title, correctAnswer));
    }

    @PostMapping("/addChoiceOfAnswerQuestion")
    public RequestResultJSON<String> addChoiceOfAnswerQuestion(@RequestParam Integer userId,
                                                               @RequestParam Integer testId,
                                                               @RequestParam String title,
                                                               @RequestParam String correctAnswer,
                                                               @RequestParam String secondOption,
                                                               @RequestParam String thirdOption,
                                                               @RequestParam String fourthOption) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addFreeEntryQuestion called. userId=%s, testId=%s, title=%s,  correctAnswer=%s, secondOption=%s, thirdOption=%s, fourthOption=%s",
                        userId, testId, title, correctAnswer, secondOption, thirdOption, fourthOption),
                () -> questionsService.addQuestion(userId, testId, QuestionType.choiceOfAnswer, title, correctAnswer, secondOption, thirdOption, fourthOption));
    }


}
