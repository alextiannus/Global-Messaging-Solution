package com.Entity.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Donnie on 2017/2/20.
 */
@Getter
@Setter
public class MsgResult {
    private String errcode;
    private String errmsg;
    private String invaliduser;
    private String invalidparty;
    private String invalidtag;

}
