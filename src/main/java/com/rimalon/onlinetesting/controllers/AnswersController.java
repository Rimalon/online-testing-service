package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.AnswersJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.interfaces.AnswersService;
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
@RequestMapping("/answer-api")
public class AnswersController extends BaseController {
    private AnswersService answersService;
    private UserPermissionsService userPermissionsService;

    @Autowired
    protected AnswersController(AnswersService answersService, UserPermissionsService userPermissionsService) {
        super(log);
        this.answersService = answersService;
        this.userPermissionsService = userPermissionsService;
    }

    @PostMapping("/addAnswer")
    public RequestResultJSON<String> addAnswer(@RequestParam Integer userId,
                                               @RequestParam Integer questionId,
                                               @RequestParam(required = false) String answer) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("addAnswer called. userId=%s, questionId=%s, answer=%s", userId, questionId, answer),
                () -> answersService.addAnswer(userId, questionId, answer));
    }

    @GetMapping("/getAnswers")
    public RequestResultJSON<AnswersJSON> getAnswers(@RequestParam Integer userId) {
        return userPermissionsService.executeForLoggedUser(userId,
                String.format("getAnswers called. userId=%s", userId),
                () -> answersService.getAnswers(userId));
    }
}
