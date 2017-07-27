package com.wormwood.service;

import com.wormwood.DTO.GmsCorpDTO;
import com.wormwood.dao.WechatDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: tangbin
 * Date: 2017/7/27
 * T me: 8:35
 * Description: use wechat tables
 */
public class WechatService {

    private static Logger logger = LoggerFactory.getLogger(WechatService.class);

    @Autowired
    private WechatDao wechatDao;

    public GmsCorpDTO findByCorpid(String corpid) {

        logger.info("findByCorpid, wechatDao: " + wechatDao + ",  corpid: " + corpid);
        return wechatDao.findByCorpid(corpid);
    }

    public void insertNewCorp(GmsCorpDTO gmsCorpDTO) {
        wechatDao.insertNewCorp(gmsCorpDTO);
    }

    public void updateCorp(GmsCorpDTO gmsCorpDTO) {
        wechatDao.updateCorp(gmsCorpDTO);
    }

}
