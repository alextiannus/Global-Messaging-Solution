package com.Entity.SMS;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Donnie on 2017/2/20.
 */
@XmlRootElement(name = "returnsms")
@Getter
@Setter
public class SMSResult {
    private String returnstatus;
    private String message;
    private int remainpoint;
    private long taskID;
    private int successCounts;

}
