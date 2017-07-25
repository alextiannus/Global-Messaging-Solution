package com.wormwood.config;

import com.wormwood.DTO.User;
import com.wormwood.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Donnie on 2017/4/25.
 */

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);
    @Autowired
    private UserService userService;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    }



    private User loadUserByUsername(String username) {
        return userService.findByName(username);
    }
}
