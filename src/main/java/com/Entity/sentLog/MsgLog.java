//package com.Entity.sentLog;
//
//import com.Entity.msg.MsgResult;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.beans.BeanUtils;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.util.Date;
//
///**
// * Created by Donnie on 2017/2/20.
// */
//@Entity
//@NoArgsConstructor
//@Getter @Setter
//public class MsgLog {
//    public MsgLog(MsgResult msgResult) {
//        BeanUtils.copyProperties(msgResult, this);
//    }
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Date sendDate;
//
//    private String errcode;
//    private String errmsg;
//    private String invaliduser;
//    private String invalidparty;
//    private String invalidtag;
//
//}
