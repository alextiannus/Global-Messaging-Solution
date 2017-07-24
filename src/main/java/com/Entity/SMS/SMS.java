package com.Entity.SMS;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Donnie on 2017/2/20.
 */
@Getter@Setter
public class SMS {
    private long userid;
    private String account;
    private String password;
    private String mobile;
    private String content;
    private Date sendTime;
    private String action = "send";
    private String extno;

}
