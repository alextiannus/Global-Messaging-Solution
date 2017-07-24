package com.service;

import com.Entity.msg.MpNews;
import com.Entity.msg.MsgResult;
import com.Entity.msg.MpNewsMsg;
import com.google.gson.Gson;
import com.repository.LogRepository;
import com.util.GsonUtil;
import com.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Donnie on 2017/2/20.
 */
@Slf4j
@Service
public class MsgService {

    @Autowired
    private LogRepository logRepository;

    /**
     *
     * @param msg
     * @return
     */
    @Transactional
    public MsgResult sendTextMsg(MpNewsMsg msg, String access_token) {
        Gson gson = GsonUtil.getInstance();
        String jsonContent = gson.toJson(msg);
        String sendUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token;
        String msgResultJason = UrlUtil.urlPost(sendUrl, jsonContent);
        MsgResult msgResult = gson.fromJson(msgResultJason, MsgResult.class);
        log.debug("send msg result:{}", msgResult.getErrmsg());

//        MsgLog msgLog = new MsgLog(msgResult);
//        msgLog.setSendDate(new Date());
        return msgResult;
    }

    /**
     *
     * @param msg
     * @param access_token
     * @return
     */
    public MsgResult sendMpNews(MpNewsMsg msg, String access_token) {
        String jsonContent = GsonUtil.getInstance().toJson(msg);
        String sendUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token;
        log.debug("--------------------------jsonMsg:{}",jsonContent);
        String jsonResult = UrlUtil.urlPost(sendUrl, jsonContent);
        MsgResult msgResult = GsonUtil.getInstance().fromJson(jsonResult, MsgResult.class);
        return msgResult;
    }
}
