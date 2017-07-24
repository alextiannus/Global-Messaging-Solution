package com.Entity.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Donnie on 2017/2/17.
 */
@Getter@Setter
public class TextMsg {
    private String touser;
    private String toparty;
    private String totag;
    private String msgtype;
    private Integer agentid;
    private Text text;
    private Integer safe;

}
