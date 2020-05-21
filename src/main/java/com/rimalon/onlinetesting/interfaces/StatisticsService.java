package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;

import java.math.BigDecimal;

public interface StatisticsService {
    RequestResultJSON<Integer> getTotalUsersAmount();

    RequestResultJSON<Integer> getUsersTestedAmount(Integer testId);

    RequestResultJSON<Integer> getUsersAnsweredAllTestingQuestions(Integer testId);

    RequestResultJSON<Integer> getUsersAnsweredAllTestingQuestionsCorrectly(Integer testId);

    RequestResultJSON<BigDecimal> getUserPercentageOfCorrectAnswers(Integer userId, Integer testId);

    RequestResultJSON<BigDecimal> getUsersPercentageOfWorseThanUser(Integer userId, Integer testId);

    RequestResultJSON<BigDecimal> getUsersPercentageOfBetterThanUser(Integer userId, Integer testId);
}
