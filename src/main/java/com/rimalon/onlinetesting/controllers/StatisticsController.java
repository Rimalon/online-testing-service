package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.services.AuthServiceImpl;
import com.rimalon.onlinetesting.services.StatisticsServiceImpl;
import com.rimalon.onlinetesting.services.UserPermissionsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping("/stat-api")
public class StatisticsController extends BaseController {
    private StatisticsServiceImpl statisticsService;
    private UserPermissionsServiceImpl userPermissionsService;

    @Autowired
    public StatisticsController(StatisticsServiceImpl statisticsService, UserPermissionsServiceImpl userPermissionsService) {
        super(log);
        this.statisticsService = statisticsService;
        this.userPermissionsService = userPermissionsService;
    }

    @PostMapping("/getTotalUsersAmount")
    public RequestResultJSON<Integer> getTotalUsersAmount() {
        return execute("getTotalUsersAmount called.", statisticsService::getTotalUsersAmount);
    }

    @PostMapping("/getUsersTestedAmount")
    public RequestResultJSON<Integer> getUsersTestedAmount(Integer testId) {
        return execute(String.format("getUsersTestedAmount called. testId=%s", testId),
                () -> statisticsService.getUsersTestedAmount(testId));
    }

    @PostMapping("/getUsersAnsweredAllTestingQuestions")
    public RequestResultJSON<Integer> getUsersAnsweredAllTestingQuestions(Integer testId) {
        return execute(String.format("getUsersAnsweredAllTestingQuestions called. testId=%s", testId),
                () -> statisticsService.getUsersAnsweredAllTestingQuestions(testId));
    }

    @PostMapping("/getUsersAnsweredAllTestingQuestionsCorrectly")
    public RequestResultJSON<Integer> getUsersAnsweredAllTestingQuestionsCorrectly(Integer testId) {
        return execute(String.format("getUsersAnsweredAllTestingQuestionsCorrectly called. testId=%s", testId),
                () -> statisticsService.getUsersAnsweredAllTestingQuestionsCorrectly(testId));
    }

    @PostMapping("/getUserPercentageOfCorrectAnswers")
    public RequestResultJSON<BigDecimal> getUserPercentageOfCorrectAnswers(@NotNull Integer userId, Integer testId) {
        return userPermissionsService.executeForLoggedUser(userId, String.format("getUserPercentageOfCorrectAnswers called. userId=%s, testId=%s", userId, testId),
                () -> statisticsService.getUserPercentageOfCorrectAnswers(userId, testId));
    }

    @PostMapping("/getUsersPercentageOfWorseThanUser")
    public RequestResultJSON<BigDecimal>  getUsersPercentageOfWorseThanUser(@NotNull Integer userId, Integer testId) {
        return userPermissionsService.executeForLoggedUser(userId, String.format("getUsersPercentageOfWorseThanUser called. userId=%s, testId=%s", userId, testId),
                () -> statisticsService.getUsersPercentageOfWorseThanUser(userId, testId));
    }

    @PostMapping("/getUsersPercentageOfBetterThanUser")
    public RequestResultJSON<BigDecimal>  getUsersPercentageOfBetterThanUser(@NotNull Integer userId, Integer testId) {
        return userPermissionsService.executeForLoggedUser(userId, String.format("getUsersPercentageOfBetterThanUser called. userId=%s, testId=%s", userId, testId),
                () -> statisticsService.getUsersPercentageOfBetterThanUser(userId, testId));
    }
}
