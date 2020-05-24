package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.interfaces.AnswersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@Slf4j
@RestController
@Validated
@RequestMapping("/answer-api")
public class AnswersController extends BaseController {
    private AnswersService answersService;

    @Autowired
    protected AnswersController(AnswersService answersService, CacheHelper cacheHelper) {
        super(log, cacheHelper);
        this.answersService = answersService;
    }

    @PostMapping("/addAnswer")
    public RequestResultJSON<String> addAnswer(@RequestParam @NotEmpty String cookie,
                                               @RequestParam int questionId,
                                               @RequestParam(required = false) String answer) {
        return executeByLoggedUser(cookie, "addAnswer",
                String.format("questionId=%s, answer=%s", questionId, answer),
                (userId) -> answersService.addAnswer(userId, questionId, answer));
    }

    @GetMapping("/getTestAnswers")
    public RequestResultJSON<AnswersJSON> getTestAnswers(@RequestParam @NotEmpty String cookie,
                                                         @RequestParam int testId) {
        return executeByLoggedUser(cookie, "getTestAnswers",
                (userId) -> answersService.getAnswers(userId, testId));
    }


    @GetMapping("/getAllAnswers")
    public RequestResultJSON<AnswersJSON> getAllAnswers(@RequestParam @NotEmpty String cookie) {
        return executeByLoggedUser(cookie, "getAllAnswers",
                (userId) -> answersService.getAnswers(userId));
    }
}
