package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.exceptions.UserNotLoggedInException;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;


@Slf4j
public class UserPermissionsServiceImpl {
    private CacheHelper cacheHelper;

    public UserPermissionsServiceImpl(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
    }

    public <T> RequestResultJSON<T> executeForLoggedUser(Integer userId, String logMessage, Supplier<RequestResultJSON<T>> request) {
        if (!cacheHelper.getOnlineUsersIdList().contains(userId)) {
            return RequestResultJSON.errorResult(APIError.USER_NOT_LOGGED_IN);
        } else {
            log.debug("{}.", logMessage);
            return request.get();
        }
    }
}
