///**
// * Copyright (c) 2016-2016 All Rights Reserved.
// */
//package com.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mobilityexchange.common.config.Constants;
//import com.mobilityexchange.common.response.Response;
//import com.mobilityexchange.common.result.ResultEnum;
//import org.apache.el.stream.Optional;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Objects;
//
///**
// * token guolv
// *
// * @author ylin
// */
//public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
//    private RequestMatcher[] matchers;
//
//    public JwtAuthenticationTokenFilter(String[] urls) {
//        super("/**");
////        Objects.requireNonNull(urls);todo
//
//        matchers = new AntPathRequestMatcher[urls.length];
//        for (int i = 0; i < urls.length; i++) {
//            matchers[i] = new AntPathRequestMatcher(urls[i]);
//        }
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String token = request.getHeader(Constants.TOKEN_HEADER);
//        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
//    }
//
//    @Override
//    protected boolean requiresAuthentication(HttpServletRequest request,
//                                             HttpServletResponse response) {
//        for (RequestMatcher matcher : matchers) {
//            if (matcher.matches(request)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authResult);
//        SecurityContextHolder.setContext(context);
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              AuthenticationException failed) throws IOException, ServletException {
//        SecurityContextHolder.clearContext();
//        response.setStatus(HttpServletResponse.SC_OK);
//        String jsonResponse = new ObjectMapper().writeValueAsString(new Response<>(ResultEnum.UNAUTHORIZED));
//        response.addHeader("Content-Type", "application/json; charset=utf-8");
//        response.getWriter().print(jsonResponse);
//    }
//}