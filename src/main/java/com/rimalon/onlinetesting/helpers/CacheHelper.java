package com.rimalon.onlinetesting.helpers;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class CacheHelper {
    @Getter
    ConcurrentSkipListSet<Integer> onlineUsersIdList = new ConcurrentSkipListSet<>();

}
