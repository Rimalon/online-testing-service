package com.rimalon.onlinetesting.interfaces;

import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;

public interface AuthService {
    RequestResultJSON<Integer>  login(String username, String password);

    RequestResultJSON<String>  register(String username, String password, String passwordConfirm);

    RequestResultJSON<String>  logout(Integer userId);
}
