package com.rimalon.onlinetesting.controllers;


import com.rimalon.onlinetesting.datamodel.dto.RequestResultJSON;
import com.rimalon.onlinetesting.services.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth-api")
public class AuthController extends BaseController {
    private AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        super(log);
        this.authService = authService;
    }

    @PostMapping("/register")
    public RequestResultJSON<String> registration(@RequestParam String username,
                                                  @RequestParam String password,
                                                  @RequestParam String passwordConfirm) {
        return execute(String.format("register called. username=%s", username), () -> authService.register(username, password, passwordConfirm));
    }

    @PostMapping("/login")
    public RequestResultJSON<Integer> login(@RequestParam String username,
                                         @RequestParam String password) {
        return execute(String.format("login called. username=%s", username), () -> authService.login(username, password));
    }

    @PostMapping("/logout")
    public RequestResultJSON<String> logout(@RequestParam Integer userId) {
        return execute(String.format("logout called. userId=%s", userId), () -> authService.logout(userId));
    }
}
