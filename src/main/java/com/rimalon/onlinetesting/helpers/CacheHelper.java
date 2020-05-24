package com.rimalon.onlinetesting.helpers;

import com.rimalon.onlinetesting.datamodel.ids.UserId;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheHelper {
    @Getter
    ConcurrentHashMap<String, UserId> loggedUsers = new ConcurrentHashMap<>();

}
