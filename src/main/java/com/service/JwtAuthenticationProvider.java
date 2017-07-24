//package com.service;
//
//import com.mobilityexchange.common.domain.enums.UserTypeEnum;
//import com.mobilityexchange.common.response.Response;
//import com.mobilityexchange.common.result.ResultEnum;
//import com.mobilityexchange.common.security.api.BaseUserService;
//import com.mobilityexchange.common.security.api.TokenService;
//import com.mobilityexchange.common.utils.TokenUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//import java.util.Objects;
//
///**
// * Created by Dean on 2017/1/11.
// */
//public class JwtAuthenticationProvider implements AuthenticationProvider {
//    protected BaseUserService baseUserService;
//    protected TokenService tokenService;
//
//    public JwtAuthenticationProvider(BaseUserService baseUserService, TokenService tokenService) {
//        this.baseUserService = baseUserService;
//        this.tokenService = tokenService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
//        String token = (String) jwtAuthenticationToken.getCredentials();
//        Long userId = TokenUtil.getUserIdFromToken(token);
//        UserTypeEnum userType = TokenUtil.getUserTypeFromToken(token);
//        if (StringUtils.isBlank(token) || userId == null || userType == null) {
//            throw new BadCredentialsException(ResultEnum.ILLEGAL_TOKEN.getMessage());
//        }
//        return getAuthentication(userId, token, userType);
//    }
//
//    protected Authentication getAuthentication(Long userId, String token, UserTypeEnum userType) {
////        Objects.requireNonNull(userId);
////        Objects.requireNonNull(token);
////        Objects.requireNonNull(userType);todo
//        Response validateResult = tokenService.validateToken(token);
//        JwtAuthenticationToken authentication;
//        if (validateResult != null && validateResult.isSuccess()) {
//            Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) tokenService.getDataByKey(userId, userType.getType(), "roles");
//            authentication = new JwtAuthenticationToken(userId.toString() + userType, null, authorities);
//        } else {
//            throw new BadCredentialsException(ResultEnum.ILLEGAL_TOKEN.getMessage());
//        }
//        return authentication;
//    }
//
//    protected Response getUserResult(Long userId, UserTypeEnum userTypeEnum) {
//        return baseUserService.getUserDetailsById(userId);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
//    }
//
//    public BaseUserService getBaseUserService() {
//        return baseUserService;
//    }
//
//    public void setBaseUserService(BaseUserService baseUserService) {
//        this.baseUserService = baseUserService;
//    }
//
//    public TokenService getTokenService() {
//        return tokenService;
//    }
//
//    public void setTokenService(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }
//}
