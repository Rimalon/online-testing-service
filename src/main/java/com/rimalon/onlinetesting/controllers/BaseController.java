package com.rimalon.onlinetesting.controllers;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import org.slf4j.Logger;

import java.util.function.Supplier;


public abstract class BaseController {
    Logger log;

    protected BaseController(Logger log) {
        this.log = log;
    }
    protected  <T> RequestResultJSON<T> execute(String logMessage, Supplier<RequestResultJSON<T>> request) {
        try {
            log.debug("{}.", logMessage);
            return request.get();
        } catch (Exception e) {
            log.error("{}. {}", logMessage, e.getMessage());
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
    }
}
