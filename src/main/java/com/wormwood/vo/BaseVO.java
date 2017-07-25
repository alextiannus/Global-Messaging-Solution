package com.wormwood.vo;

import java.io.Serializable;

/**
 * Created by kasimodo on 2017-07-10.
 */
public class BaseVO implements Serializable {
    private static final long serialVersionUID = 1973476966375083716L;

    private String errmsg;
    private int errcode;

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
}
