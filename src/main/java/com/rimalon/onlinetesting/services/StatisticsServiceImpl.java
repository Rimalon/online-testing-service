package com.rimalon.onlinetesting.services;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.entities.Answer;
import com.rimalon.onlinetesting.datamodel.entities.Question;
import com.rimalon.onlinetesting.datamodel.entities.Test;
import com.rimalon.onlinetesting.datamodel.entities.User;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import com.rimalon.onlinetesting.interfaces.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private QueryHelper queryHelper;

    @Autowired
    public StatisticsServiceImpl(QueryHelper queryHelper) {
        this.queryHelper = queryHelper;
    }

    @Override
    public RequestResultJSON<Integer> getTotalUsersAmount() {
        Integer result = queryHelper.getTableRowsCount(User.class);
        if (result == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            return new RequestResultJSON<>(true, result, null);
        }
    }

    @Override
    public RequestResultJSON<Integer> getUsersTestedAmount(int testId) {
        List<Answer> answersList = getAnswerListByTestId(testId);
        if (answersList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        return new RequestResultJSON<>(true, getUsersAnswerMap(answersList).size(), null);
    }

    @Override
    public RequestResultJSON<Long> getUsersAnsweredAllTestingQuestions(int testId) {
        return getUsersAmountAnsweredAllTestingQuestionByAnswerPredicate(testId, t -> true);
    }

    @Override
    public RequestResultJSON<Long> getUsersAnsweredAllTestingQuestionsCorrectly(int testId) {
        return getUsersAmountAnsweredAllTestingQuestionByAnswerPredicate(testId, Answer::getIsCorrect);
    }

    private RequestResultJSON<Long> getUsersAmountAnsweredAllTestingQuestionByAnswerPredicate(int testId, Predicate<Answer> answerPredicate) {
        List<Integer> questionsIdsList = getQuestionIdsListByTestId(testId);
        if (questionsIdsList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        List<Answer> answersList = getAnswersListByQuestionIds(questionsIdsList, null);
        if (answersList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        Map<UserId, List<Answer>> usersAnswersMap = getUsersAnswerMap(answersList);
        long result = usersAnswersMap.values().stream().filter(list -> list.stream().map(Answer::getQuestionId).collect(Collectors.toList()).containsAll(questionsIdsList))
                .filter(list -> list.stream().allMatch(answerPredicate)).count();
        return new RequestResultJSON<>(true, result, null);
    }

    @Override
    public RequestResultJSON<Double> getUserPercentageOfCorrectAnswers(UserId userId, int testId) {
        List<Integer> questionsIdsList = getQuestionIdsListByTestId(testId);
        if (questionsIdsList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        List<Answer> answersList = getAnswersListByQuestionIds(questionsIdsList, userId);
        if (answersList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        if (answersList.isEmpty()) {
            return RequestResultJSON.errorResult(APIError.USER_DONT_HAVE_ANSWERS);
        }
        return new RequestResultJSON<>(true, (double) (answersList.stream().filter(Answer::getIsCorrect).count() / (questionsIdsList.size())) * 100, null);
    }

    @Override
    public RequestResultJSON<Double> getUsersPercentageOfWorseThanUser(UserId userId, int testId) {
        return getPercentageOfUsersCompared(userId, testId, (anotherUsersCorrectAnswers, currentUserCorrectAnswers) -> anotherUsersCorrectAnswers < currentUserCorrectAnswers);
    }

    @Override
    public RequestResultJSON<Double> getUsersPercentageOfBetterThanUser(UserId userId, int testId) {
        return getPercentageOfUsersCompared(userId, testId, (anotherUsersCorrectAnswers, currentUserCorrectAnswers) -> anotherUsersCorrectAnswers > currentUserCorrectAnswers);
    }


    private RequestResultJSON<Double> getPercentageOfUsersCompared(UserId userId, int testId, BiPredicate<Long, Long> predicate) {
        List<Answer> answersList = getAnswerListByTestId(testId);
        if (answersList == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        Map<UserId, List<Answer>> usersAnswersMap = getUsersAnswerMap(answersList);
        List<Answer> userCorrectAnswerList = usersAnswersMap.get(userId);
        if (userCorrectAnswerList == null) {
            return RequestResultJSON.errorResult(APIError.USER_DONT_HAVE_ANSWERS);
        }
        long userCorrectAnswersCount = userCorrectAnswerList.stream().filter(Answer::getIsCorrect).count();
        long suitableUsers =
                usersAnswersMap.entrySet().stream()
                        .filter(e -> !e.getKey().equals(userId))
                        .filter(e -> predicate.test(e.getValue().stream().filter(Answer::getIsCorrect).count(), userCorrectAnswersCount)).count();
        return new RequestResultJSON<>(true, (double) (suitableUsers / (usersAnswersMap.keySet().size() - 1)) * 100, null);
    }

    private List<Integer> getQuestionIdsListByTestId(Integer testId) {
        List<Question> questionList = queryHelper.getListObjectsByJoinClause(Question.class, Test.class, "Test.id = Question.testId AND Test.id = ?", new Object[]{testId});
        if (questionList == null) {
            return null;
        }
        return questionList.stream().map(Question::getId).collect(Collectors.toList());
    }

    private List<Answer> getAnswersListByQuestionIds(List<Integer> questionsIdsList, UserId userId) {
        return queryHelper.getListObjectsByWhereClause(Answer.class, String.format("Answer.questionId IN (%s) AND (? IS NULL OR Answer.userId = ?)",
                questionsIdsList.stream().map(Object::toString).collect(Collectors.joining(","))), new Object[]{userId, userId});
    }

    private List<Answer> getAnswerListByTestId(Integer testId) {
        List<Integer> questionsIdsList = getQuestionIdsListByTestId(testId);
        if (questionsIdsList == null) {
            return null;
        }
        return getAnswersListByQuestionIds(questionsIdsList, null);
    }

    private Map<UserId, List<Answer>> getUsersAnswerMap(List<Answer> answersList) {
        Map<UserId, List<Answer>> result = new HashMap<>();
        for (Answer answer : answersList) {
            result.computeIfAbsent(answer.getUserId(), k -> new ArrayList<>());
            result.get(answer.getUserId()).add(answer);
        }
        return result;
    }
}
