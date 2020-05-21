package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.QuestionType;
import com.rimalon.onlinetesting.services.QuestionsServiceImpl;
import com.rimalon.onlinetesting.services.UserPermissionsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@Slf4j
@RestController
@RequestMapping("/quest-api")
public class QuestionsController extends BaseController {
    private QuestionsServiceImpl questionsService;
    private UserPermissionsServiceImpl userPermissionsService;


    @Autowired
    public QuestionsController(QuestionsServiceImpl questionsService, UserPermissionsServiceImpl userPermissionsService) {
        super(log);
        this.questionsService = questionsService;
        this.userPermissionsService = userPermissionsService;
    }


    @PostMapping("/addChoiceOfAnswerQuestion")
    public RequestResultJSON<String> addChoiceOfAnswerQuestion(@NotNull @RequestParam Integer userId,
                                                 @RequestParam Integer testId,
                                                 @RequestParam String question,
                                                 @RequestParam String answer) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addChoiceOfAnswerQuestion called. userId=%s, testId=%s, question=%s, answer=%s", userId, testId, question, answer),
                () -> questionsService.addQuestion(userId, testId, QuestionType.choiceOfAnswer, question, answer));
    }

    @PostMapping("/addFreeEntryQuestion")
    public RequestResultJSON<String> addFreeEntryQuestion (@NotNull @RequestParam Integer userId,
                                                           @RequestParam Integer testId,
                                                           @RequestParam String question,
                                                           @RequestParam String answer,
                                                           @RequestParam String[] answerOptions) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addFreeEntryQuestion called. userId=%s, testId=%s, question=%s, answer=%s", userId, testId, question, answer),
                () -> questionsService.addQuestion(userId, testId, QuestionType.freeEntry, question, answer));
    }



}
