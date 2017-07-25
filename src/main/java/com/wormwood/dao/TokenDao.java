package com.wormwood.dao;

import com.wormwood.DTO.TokenDTO;
import org.apache.ibatis.annotations.*;

/**
 * Created by kasimodo on 2017-07-04.
 */

@Mapper
public interface TokenDao {
    @Insert("INSERT INTO TOKEN(system_name, token, expire_time) " +
            "VALUES(#{systemName}, #{token},date_add(now(), interval 15 minute))")
    void insert(TokenDTO tokenDTO);

    @Select("SELECT * FROM TOKEN where system_name=#{systemName}")
    @Results({
            @Result(column = "system_name", property = "systemName"),
            @Result(column = "token", property = "token"),
            @Result(column = "expire_time", property = "expireTime")
    })
    TokenDTO findByName(String systemName);

    @Select("SELECT count(1) FROM TOKEN where token=#{token} and expire_time>=now()")
    int checkToken(String token);


    @Update("update TOKEN set token=#{token},expire_time=date_add(now(), interval 15 minute) where system_name=#{systemName}")
    void updateToken(@Param("token") String token, @Param("systemName") String systemName);
}
