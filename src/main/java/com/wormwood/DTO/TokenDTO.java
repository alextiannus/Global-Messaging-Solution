package com.wormwood.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kasimodo on 2017-07-04.
 */
public class TokenDTO implements Serializable{

    private static final long serialVersionUID = -9205744351346483353L;
    private String systemName;

    private String token;

    private Date expireTime;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
