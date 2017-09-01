package com.wormwood.DTO;

import java.util.List;

/**
 * User: tangbin
 * Date: 2017/9/1
 * T me: 9:33
 * Description: To change this template use File | Settings | File Templates.
 */
public class WechatUserMsg {

    private int errcode;
    private String errmsg;
    private List<WechatUser> userlist;

    public WechatUserMsg() {
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<WechatUser> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<WechatUser> userlist) {
        this.userlist = userlist;
    }
}
