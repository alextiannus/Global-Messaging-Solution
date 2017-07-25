package com.wormwood.dao;

import com.wormwood.DTO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Donnie on 2017/4/25.
 */
@Mapper
public interface UserDao {
    @Select("SELECT *  FROM user where username=#{name}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "org_code", property = "companyOrgCode"),
            @Result(column = "co_name", property = "companyName"),
            @Result(column = "role", property = "role"),
            @Result(column = "role_type", property = "roleType"),
            @Result(column = "email", property = "email")
    })
    User findByName(String name);


}
