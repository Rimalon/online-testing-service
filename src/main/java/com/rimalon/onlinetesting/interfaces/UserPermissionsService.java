package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;

import java.util.function.Supplier;

public interface UserPermissionsService {
    <T> RequestResultJSON<T> executeForLoggedUser(Integer userId, String logMessage, Supplier<RequestResultJSON<T>> request);
}
