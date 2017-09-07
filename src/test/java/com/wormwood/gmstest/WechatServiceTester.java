package com.wormwood.gmstest;

/**
 * User: tangbin
 * Date: 2017/9/4
 * T me: 11:18
 * Description: To change this template use File | Settings | File Templates.
 */

import com.wormwood.DTO.GmsCorpDTO;
import com.wormwood.GMSApplication;
import com.wormwood.service.WechatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GMSApplication.class)
@ContextConfiguration
public class WechatServiceTester {

    @Autowired
    private WechatService wechatService;

    @Test
    public void testUpdateCorpInfo() {
        GmsCorpDTO gmsCorp = new GmsCorpDTO();
        gmsCorp.setCorpid("go-test-id1");
        gmsCorp.setCorpsecret("go-test-id1-123456");
        gmsCorp.setCrtBy("SYS");
        gmsCorp.setCrtDate(new Date());
        wechatService.updateCorp(gmsCorp);
    }


    @Test
    public void getAllCorpUser() {
        wechatService.getAllCorpUser(null);
    }

    @Test
    public void getDepartmentIdsByNames() {
        String result = wechatService.getDepartmentIdsByNames(null, "globalORETest | globalORETest");
        System.out.println("----------------result:" + result);
    }

}


