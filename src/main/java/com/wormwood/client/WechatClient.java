package com.wormwood.client;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by kasimodo on 2017-07-25.
 */

@FeignClient(name = "weiChatAPI", url = "https://qyapi.weixin.qq.com/cgi-bin")
public interface WechatClient {
}
