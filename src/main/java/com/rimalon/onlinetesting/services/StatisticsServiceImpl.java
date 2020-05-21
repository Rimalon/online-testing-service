package com.rimalon.onlinetesting.services;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.interfaces.StatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public RequestResultJSON<Integer> getTotalUsersAmount() {
        return null;
    }

    @Override
    public RequestResultJSON<Integer> getUsersTestedAmount(Integer testId) {
        return null;
    }

    @Override
    public RequestResultJSON<Integer> getUsersAnsweredAllTestingQuestions(Integer testId) {
        return null;
    }

    @Override
    public RequestResultJSON<Integer> getUsersAnsweredAllTestingQuestionsCorrectly(Integer testId) {
        return null;
    }

    @Override
    public RequestResultJSON<BigDecimal> getUserPercentageOfCorrectAnswers(Integer userId, Integer testId) {
        return null;
    }

    @Override
    public RequestResultJSON<BigDecimal> getUsersPercentageOfWorseThanUser(Integer userId, Integer testId) {
        return null;
    }

    @Override
    public RequestResultJSON<BigDecimal> getUsersPercentageOfBetterThanUser(Integer userId, Integer testId) {
        return null;
    }
}
