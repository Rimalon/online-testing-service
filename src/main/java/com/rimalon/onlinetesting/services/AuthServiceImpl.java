package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.LoginResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.entities.User;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.datamodel.ids.UserId;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import com.rimalon.onlinetesting.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;


@Service
public class AuthServiceImpl implements AuthService {
    private static final char[] characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int COOKIE_LENGTH = 256;

    private BCryptPasswordEncoder passwordEncoder;
    private CacheHelper cacheHelper;
    private QueryHelper queryHelper;

    @Autowired
    public AuthServiceImpl(QueryHelper queryHelper, CacheHelper cacheHelper) {
        this.queryHelper = queryHelper;
        this.cacheHelper = cacheHelper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public RequestResultJSON<LoginResultJSON> login(String username, String password) {
        User user = queryHelper.getObjectByWhereClause(User.class, "username = ?", new Object[]{username.toLowerCase()});
        if (user == null) {
            return RequestResultJSON.errorResult(APIError.INCORRECT_USERNAME_OR_PASSWORD);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return RequestResultJSON.errorResult(APIError.INCORRECT_USERNAME_OR_PASSWORD);
        }
        if (cacheHelper.getLoggedUsers().containsValue(user.getId())) {
            return RequestResultJSON.errorResult(APIError.USER_ALREADY_LOGGED_IN);
        } else {
            String cookie = generateCookieAndLogin(user.getId());
            return new RequestResultJSON<>(true, new LoginResultJSON(cookie), null);
        }
    }

    @Override
    public RequestResultJSON<String> register(String username, String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            return RequestResultJSON.errorResult(APIError.PASSWORD_MISMATCH);
        }
        User user = queryHelper.getObjectByWhereClause(User.class, "username = ?", new Object[]{username.toLowerCase()});
        if (user != null) {
            return RequestResultJSON.errorResult(APIError.USER_ALREADY_REGISTERED);
        }

        boolean result = queryHelper.save(User.class, "(username, password)", new Object[]{username.toLowerCase(), passwordEncoder.encode(password)});

        if (!result) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        } else {
            return new RequestResultJSON<>(true, "Registration completed successfully", null);
        }
    }

    @Override
    public RequestResultJSON<String> logout(String cookie) {
        if (!cacheHelper.getLoggedUsers().containsKey(cookie)) {
            return RequestResultJSON.errorResult(APIError.USER_NOT_LOGGED_IN);
        }
        cacheHelper.getLoggedUsers().remove(cookie);
        return new RequestResultJSON<>(true, "Logout completed successfully", null);
    }

    public String generateCookieAndLogin(UserId userId) {
        String cookie = generateRandomCookie(COOKIE_LENGTH);
        while (cacheHelper.getLoggedUsers().putIfAbsent(cookie, userId) != null) {
            cookie = generateRandomCookie(COOKIE_LENGTH);
        }
        return cookie;
    }

    private String generateRandomCookie(int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }
}
