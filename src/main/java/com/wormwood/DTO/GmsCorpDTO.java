package com.wormwood.DTO;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * User: tangbin
 * Date: 2017/7/27
 * T me: 8:35
 * Description: use wechat tables
 */
public class GmsCorpDTO implements Serializable {


    public GmsCorpDTO() {
    }

    @Id
    @GeneratedValue
    private Integer id;
    private String corpid;
    private String corpsecret;
    private int agentid;
    private String access_token;
    private Date expires_in;

    private Date crtDate;
    private String crtBy;
    private Date updDate;
    private String udpBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpsecret() {
        return corpsecret;
    }

    public void setCorpsecret(String corpsecret) {
        this.corpsecret = corpsecret;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Date expires_in) {
        this.expires_in = expires_in;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getCrtBy() {
        return crtBy;
    }

    public void setCrtBy(String crtBy) {
        this.crtBy = crtBy;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUdpBy() {
        return udpBy;
    }

    public void setUdpBy(String udpBy) {
        this.udpBy = udpBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("corpid", corpid)
                .append("corpsecret", corpsecret)
                .append("agentid", agentid)
                .append("access_token", access_token)
                .append("expires_in", expires_in)
                .append("crtDate", crtDate)
                .append("crtBy", crtBy)
                .append("updDate", updDate)
                .append("udpBy", udpBy)
                .toString();
    }
}
