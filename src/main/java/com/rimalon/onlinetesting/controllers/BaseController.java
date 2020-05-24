package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import org.slf4j.Logger;

import java.util.function.Function;
import java.util.function.Supplier;


public abstract class BaseController {
    private Logger log;
    private CacheHelper cacheHelper;

    protected BaseController(Logger log, CacheHelper cacheHelper) {
        this.log = log;
        this.cacheHelper = cacheHelper;
    }

    protected <T> RequestResultJSON<T> execute(String methodName, Supplier<RequestResultJSON<T>> supplier) {
        return execute(methodName, "", supplier);
    }

    protected <T> RequestResultJSON<T> execute(String methodName, String params, Supplier<RequestResultJSON<T>> supplier) {
        try {
            log.debug("{} called. {}", methodName, params);
            return supplier.get();
        } catch (Exception e) {
            log.error("{} error. {}", methodName, e.getMessage());
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
    }


    protected <T> RequestResultJSON<T> executeByLoggedUser(String cookie, String methodName, Function<UserId, RequestResultJSON<T>> function) {
        return executeByLoggedUser(cookie, methodName, "", function);
    }

    protected <T> RequestResultJSON<T> executeByLoggedUser(String cookie, String methodName, String params, Function<UserId, RequestResultJSON<T>> function) {
        if (cookie == null) {
            return RequestResultJSON.errorResult(APIError.WRONG_COOKIE);
        }
        if (!cacheHelper.getLoggedUsers().containsKey(cookie)) {
            return RequestResultJSON.errorResult(APIError.USER_NOT_LOGGED_IN);
        } else {
            UserId loggedUserId = cacheHelper.getLoggedUsers().get(cookie);
            log.debug("{} called. userId={}, {}", methodName, loggedUserId, params);
            return function.apply(loggedUserId);
        }
    }
}
