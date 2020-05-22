package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.interfaces.UserPermissionsService;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;


@Slf4j
public class UserPermissionsServiceImpl implements UserPermissionsService {
    private CacheHelper cacheHelper;

    public UserPermissionsServiceImpl(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
    }

    @Override
    public <T> RequestResultJSON<T> executeForLoggedUser(Integer userId, String logMessage, Supplier<RequestResultJSON<T>> request) {
        log.debug("{}.", logMessage);
        if (!cacheHelper.getOnlineUsersIdList().contains(userId)) {
            return RequestResultJSON.errorResult(APIError.USER_NOT_LOGGED_IN);
        } else {
            return request.get();
        }
    }
}
