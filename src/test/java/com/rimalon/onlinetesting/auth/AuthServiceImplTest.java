package com.rimalon.onlinetesting.auth;


import com.rimalon.onlinetesting.BaseTest;
import com.rimalon.onlinetesting.datamodel.dto.LoginResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.services.AuthServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AuthServiceImplTest extends BaseTest {
    @Autowired
    AuthServiceImpl userServiceImpl;

    @Test
    public void registerTest() {
        String username = "registerTestUser";
        String correctPassword = "qwerty";
        int result = userServiceImpl.register(username, correctPassword, correctPassword).getSuccess();
        assertEquals(1, result);
        result = userServiceImpl.register(username, correctPassword, correctPassword).getSuccess();
        assertEquals(0, result);
    }


    @Test
    public void loginAndLogoutTest() {
        String username = "loginTestUser";
        String correctPassword = "qwerty";
        String incorrectPassword = "12314";
        userServiceImpl.register(username, correctPassword, correctPassword);
        RequestResultJSON<LoginResultJSON> loginResult = userServiceImpl.login(username, correctPassword);
        String userCookie = loginResult.getResult().getCookie();
        assertEquals(1, loginResult.getSuccess());
        loginResult = userServiceImpl.login(username, correctPassword);
        assertEquals(0, loginResult.getSuccess());
        assertEquals(APIError.USER_ALREADY_LOGGED_IN.getMessage(), loginResult.getError());
        RequestResultJSON<String> logoutResult = userServiceImpl.logout(userCookie);
        assertEquals(1, logoutResult.getSuccess());
        logoutResult = userServiceImpl.logout(userCookie);
        assertEquals(0, logoutResult.getSuccess());
        loginResult = userServiceImpl.login(username, incorrectPassword);
        assertEquals(0, loginResult.getSuccess());
        assertEquals(APIError.INCORRECT_USERNAME_OR_PASSWORD.getMessage(), loginResult.getError());
    }
}
