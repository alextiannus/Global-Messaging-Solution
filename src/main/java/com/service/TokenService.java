///**
// * Copyright (c) 2016-2016 All Rights Reserved.
// */
//package com.service;
//
//import com.mobilityexchange.common.response.Response;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// * 对Token进行操作的接口
// *
// * @author ylin
// */
//public interface TokenService {
//    /**
//     * 生成token
//     *
//     * @param userId
//     * @param userType 用户类型 标示是哪种用户
//     * @return
//     */
//    String generateToken(Long userId, String userType, Collection<? extends GrantedAuthority> authorities);
//
//    /**
//     * 验证token
//     *
//     * @param token
//     * @return
//     */
//    Response validateToken(String token);
//
//    /**
//     * 清除token
//     *
//     * @param token
//     */
//    void deleteToken(String token);
//
//    /**
//     * 获取数据
//     *
//     * @param userId
//     * @param userType
//     * @return
//     */
//    Object getDataByKey(Long userId, String userType, String key);
//
//}
