//package com.Entity.sentLog;
//
//import com.Entity.SMS.SMSResult;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.*;
//import org.springframework.beans.BeanUtils;
//
//import javax.persistence.*;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * Created by Donnie on 2017/2/20.
// */
//@Entity
//@Getter @Setter
//@NoArgsConstructor
//@Table(name = "sms_log")
//public class SMSLog {
//    public SMSLog(SMSResult smsResult) {
//        BeanUtils.copyProperties(smsResult, this);
//    }
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Date sendDate;
//
//    private String returnstatus;
//    private String message;
//    private int remainpoint;
//    private long taskID;
//    private int successCounts;
//
//
//}
