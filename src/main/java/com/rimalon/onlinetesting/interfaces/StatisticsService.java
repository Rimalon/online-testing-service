package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.ids.UserId;

public interface StatisticsService {
    RequestResultJSON<Integer> getTotalUsersAmount();

    RequestResultJSON<Integer> getUsersTestedAmount(int testId);

    RequestResultJSON<Long> getUsersAnsweredAllTestingQuestions(int testId);

    RequestResultJSON<Long> getUsersAnsweredAllTestingQuestionsCorrectly(int testId);

    RequestResultJSON<Double> getUserPercentageOfCorrectAnswers(UserId userId, int testId);

    RequestResultJSON<Double> getUsersPercentageOfWorseThanUser(UserId userId, int testId);

    RequestResultJSON<Double> getUsersPercentageOfBetterThanUser(UserId userId, int testId);
}
