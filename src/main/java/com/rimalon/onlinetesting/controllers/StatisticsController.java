package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.interfaces.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/stat-api")
public class StatisticsController extends BaseController {
    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, CacheHelper cacheHelper) {
        super(log, cacheHelper);
        this.statisticsService = statisticsService;
    }

    @GetMapping("/getTotalUsersAmount")
    public RequestResultJSON<Integer> getTotalUsersAmount() {
        return execute("getTotalUsersAmount", statisticsService::getTotalUsersAmount);
    }

    @GetMapping("/getUsersTestedAmount")
    public RequestResultJSON<Integer> getUsersTestedAmount(@RequestParam int testId) {
        return execute("getUsersTestedAmount", String.format("testId=%s", testId),
                () -> statisticsService.getUsersTestedAmount(testId));
    }

    @GetMapping("/getUsersAnsweredAllTestingQuestions")
    public RequestResultJSON<Long> getUsersAnsweredAllTestingQuestions(@RequestParam int testId) {
        return execute("getUsersAnsweredAllTestingQuestions", String.format("testId=%s", testId),
                () -> statisticsService.getUsersAnsweredAllTestingQuestions(testId));
    }

    @GetMapping("/getUsersAnsweredAllTestingQuestionsCorrectly")
    public RequestResultJSON<Long> getUsersAnsweredAllTestingQuestionsCorrectly(@RequestParam int testId) {
        return execute("getUsersAnsweredAllTestingQuestionsCorrectly", String.format("testId=%s", testId),
                () -> statisticsService.getUsersAnsweredAllTestingQuestionsCorrectly(testId));
    }

    @GetMapping("/getUserPercentageOfCorrectAnswers")
    public RequestResultJSON<Double> getUserPercentageOfCorrectAnswers(@RequestParam String cookie,
                                                                       @RequestParam int testId) {
        return executeByLoggedUser(cookie, "getUserPercentageOfCorrectAnswers", String.format("testId=%s", testId),
                (userId) -> statisticsService.getUserPercentageOfCorrectAnswers(userId, testId));
    }

    @GetMapping("/getUsersPercentageOfWorseThanUser")
    public RequestResultJSON<Double> getUsersPercentageOfWorseThanUser(@RequestParam String cookie,
                                                                       @RequestParam int testId) {
        return executeByLoggedUser(cookie, "getUsersPercentageOfWorseThanUser", String.format("testId=%s", testId),
                (userId) -> statisticsService.getUsersPercentageOfWorseThanUser(userId, testId));
    }

    @GetMapping("/getUsersPercentageOfBetterThanUser")
    public RequestResultJSON<Double> getUsersPercentageOfBetterThanUser(@RequestParam String cookie,
                                                                        @RequestParam int testId) {
        return executeByLoggedUser(cookie, "getUsersPercentageOfBetterThanUser", String.format("testId=%s", testId),
                (userId) -> statisticsService.getUsersPercentageOfBetterThanUser(userId, testId));
    }
}
