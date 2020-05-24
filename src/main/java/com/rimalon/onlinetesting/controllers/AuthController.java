package com.rimalon.onlinetesting.controllers;


import com.rimalon.onlinetesting.datamodel.dto.LoginResultJSON;
import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.helpers.CacheHelper;
import com.rimalon.onlinetesting.interfaces.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@Slf4j
@RestController
@Validated
@RequestMapping("/auth-api")
public class AuthController extends BaseController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService, CacheHelper cacheHelper) {
        super(log, cacheHelper);
        this.authService = authService;
    }

    @PostMapping("/register")
    public RequestResultJSON<String> register(@RequestParam @NotEmpty String username,
                                              @RequestParam @NotEmpty String password,
                                              @RequestParam @NotEmpty String passwordConfirm) {
        return execute("register", String.format("username=%s", username), () -> authService.register(username, password, passwordConfirm));
    }

    @PostMapping("/login")
    public RequestResultJSON<LoginResultJSON> login(@RequestParam @NotEmpty String username,
                                                    @RequestParam @NotEmpty String password) {
        return execute("login", String.format("username=%s", username), () -> authService.login(username, password));
    }

    @PostMapping("/logout")
    public RequestResultJSON<String> logout(@RequestParam @NotEmpty String cookie) {
        return executeByLoggedUser(cookie, "logout", (userId) -> authService.logout(cookie));
    }
}
