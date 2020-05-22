package com.rimalon.onlinetesting.services;


import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.datamodel.enums.APIError;
import com.rimalon.onlinetesting.interfaces.AuthService;
import com.rimalon.onlinetesting.datamodel.entities.User;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.helpers.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

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
    public RequestResultJSON<Integer> login(String username, String password) {
        User user = queryHelper.getObjectByWhereClause(User.class, "username = ?", new Object[]{username.toLowerCase()});
        if (user == null) {
            return RequestResultJSON.errorResult(APIError.INCORRECT_USERNAME_OR_PASSWORD);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return RequestResultJSON.errorResult(APIError.INCORRECT_USERNAME_OR_PASSWORD);
        }
        if (cacheHelper.getOnlineUsersIdList().contains(user.getId())) {
            return RequestResultJSON.errorResult(APIError.USER_ALREADY_LOGGED_IN);
        } else {
            cacheHelper.getOnlineUsersIdList().add(user.getId());
            return new RequestResultJSON<>(true, user.getId(), null);
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
    public RequestResultJSON<String> logout(Integer userId) {
        User user = queryHelper.getById(User.class, userId);
        if (user == null) {
            return RequestResultJSON.errorResult(APIError.INTERNALL_ERROR);
        }
        if (!cacheHelper.getOnlineUsersIdList().contains(user.getId())) {
            return RequestResultJSON.errorResult(APIError.USER_ALREADY_LOGGED_OUT);
        } else {
            cacheHelper.getOnlineUsersIdList().remove(user.getId());
            return new RequestResultJSON<>(true, "", null);
        }
    }
}
