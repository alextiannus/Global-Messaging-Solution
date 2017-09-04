package com.wormwood.gmstest;

/**
 * User: tangbin
 * Date: 2017/9/4
 * T me: 11:18
 * Description: To change this template use File | Settings | File Templates.
 */

import com.wormwood.GMSApplication;
import com.wormwood.service.WechatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GMSApplication.class)
@ContextConfiguration
public class WechatServiceTester {

    @Autowired
    private WechatService wechatService;

    @Test
    public void getAllCorpUser() {
        wechatService.getAllCorpUser(null);
    }

}


