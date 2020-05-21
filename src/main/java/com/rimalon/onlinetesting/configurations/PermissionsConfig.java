package com.rimalon.onlinetesting.configurations;

import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.services.UserPermissionsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionsConfig {

    @Bean
    public UserPermissionsServiceImpl userPermissionsService(CacheHelper cacheHelper) {
        return new UserPermissionsServiceImpl(cacheHelper);
    }
}
