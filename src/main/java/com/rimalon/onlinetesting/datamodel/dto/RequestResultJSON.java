package com.rimalon.onlinetesting.datamodel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import lombok.Getter;

public class RequestResultJSON<T> {
    @Getter
    @JsonProperty("success")
    protected int success;

    @Getter
    @JsonProperty("return")
    protected T result;

    @Getter
    @JsonProperty("error")
    protected String error;


    @JsonCreator
    public RequestResultJSON(
            @JsonProperty("success") boolean success,
            @JsonProperty("return") T result,
            @JsonProperty("error") String error) {
        this.result = result;
        this.success = success ? 1 : 0;
        this.error = error;
    }

    public static <T> RequestResultJSON<T> errorResult(APIError error){
        return new RequestResultJSON<>(false, null, error.getMessage());
    }
}
