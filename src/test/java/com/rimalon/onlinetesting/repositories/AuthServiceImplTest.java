package com.rimalon.onlinetesting.repositories;


import com.rimalon.onlinetesting.OnlineTestingApplication;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.exceptions.OnlineTestingException;
import com.rimalon.onlinetesting.services.AuthServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineTestingApplication.class)
public class AuthServiceImplTest {
    @Autowired
    AuthServiceImpl userServiceImpl;

    @Test
    public void registerTest() throws OnlineTestingException {
        String username = "registerTestUser";
        String correctPassword = "qwerty";
        int result = userServiceImpl.register(username, correctPassword, correctPassword).getSuccess();
        assertEquals(1, result);
        result = userServiceImpl.register(username, correctPassword, correctPassword).getSuccess();
        assertEquals(0, result);
    }


    @Test
    public void loginAndLogoutTest() throws OnlineTestingException {
        String username = "loginTestUser";
        String correctPassword = "qwerty";
        String incorrectPassword = "12314";
        userServiceImpl.register(username, correctPassword, correctPassword);
        RequestResultJSON result = userServiceImpl.login(username, correctPassword);
        Integer userId = (Integer)result.getResult();
        assertEquals(1, result.getSuccess());
        result = userServiceImpl.login(username, correctPassword);
        assertEquals(0, result.getSuccess());
        assertEquals(APIError.USER_ALREADY_LOGGED_IN.getMessage(), result.getError());
        result = userServiceImpl.logout(userId);
        assertEquals(1, result.getSuccess());
        result = userServiceImpl.logout(userId);
        assertEquals(0, result.getSuccess());
        result = userServiceImpl.login(username, incorrectPassword);
        assertEquals(0, result.getSuccess());
        assertEquals(APIError.INCORRECT_USERNAME_OR_PASSWORD.getMessage(), result.getError());
    }
}
