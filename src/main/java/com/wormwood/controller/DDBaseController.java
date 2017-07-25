package com.wormwood.controller;

import com.wormwood.client.DingDingClient;
import com.wormwood.vo.DDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * Created by kasimodo on 2017-07-10.
 */

@Controller
public class DDBaseController {

    @Autowired
    private DingDingClient dingDingClient;

    @Value("${dingding.corpid}")
    private String corpid;

    @Value("${dignding.corpsecret}")
    private String corpsecret;

    protected DDToken getToken() {
        return dingDingClient.getToken(corpid, corpsecret);
    }
}
