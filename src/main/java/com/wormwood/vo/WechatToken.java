package com.wormwood.vo;


public class WechatToken extends BaseVO {

    private String access_token;
    private long expires_in;

    public WechatToken() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String toSring() {
        return new StringBuilder("access_token: ").append(access_token).append(", expires_in: ").append(expires_in).toString();
    }

}
