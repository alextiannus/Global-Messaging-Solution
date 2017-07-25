package com.wormwood.client;

import com.wormwood.vo.WechatToken;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by kasimodo on 2017-07-25.
 */

@FeignClient(name = "weiChatAPI", url = "https://qyapi.weixin.qq.com/cgi-bin")
public interface WechatClient {

    @RequestMapping(method = RequestMethod.GET, value = "gettoken")
    WechatToken getToken(@RequestParam(value = "corpid") String corpid, @RequestParam(value = "corpsecret") String corpsecret);



}
