package com.wormwood.dao;

import com.wormwood.DTO.GmsCorpDTO;
import org.apache.ibatis.annotations.*;

/**
 * User: tangbin
 * Date: 2017/7/27
 * T me: 8:35
 * Description: use wechat tables
 */
@Mapper
public interface WechatDao {

    @Select("SELECT *  FROM gms_corp where corpid=#{corpid}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "corpid", property = "corpid"),
            @Result(column = "corpsecret", property = "corpsecret"),
            @Result(column = "agentid", property = "agentid"),
            @Result(column = "access_token", property = "access_token"),
            @Result(column = "expires_in", property = "expires_in"),
            @Result(column = "crt_on", property = "crtDate"),
            @Result(column = "crt_by", property = "crtBy"),
            @Result(column = "upd_on", property = "updDate"),
            @Result(column = "upd_by", property = "udpBy")
    })
    public GmsCorpDTO findByCorpid(String corpid);


    @Insert("insert into gms_corp(corpid, corpsecret, agentid, crt_on, crt_by) values(#{corpid}, #{corpsecret}, #{agentid}, #{crtDate} ,#{crtBy}) ")
    public void insertNewCorp(GmsCorpDTO gmsCorpDTO);

    @Update("update gms_corp  set corpsecret=#{corpsecret},  agentid=#{agentid},  upd_on=#{updDate}, upd_by=#{udpBy}")
    public void updateCorp(GmsCorpDTO gmsCorpDTO);

}
