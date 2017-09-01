package com.wormwood.service;

import com.google.common.collect.Lists;
import com.wormwood.DTO.*;
import com.wormwood.dao.WechatDao;
import com.wormwood.util.GsonUtil;
import com.wormwood.util.UrlUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    public List<DepartmentDetail> getDepartmentList(String accessToken) {
        String getDepartList = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + accessToken;
        String departList = UrlUtil.urlPost(getDepartList, "");
        logger.info("sendTextMessage departList: " + departList);
        if (StringUtils.isNotBlank(departList)) {
            DepartmentMsg departmentMsg = GsonUtil.getInstance().fromJson(departList, DepartmentMsg.class);
            if (departmentMsg != null) {
                List<DepartmentDetail> department = departmentMsg.getDepartment();
                for (DepartmentDetail item : department) {
                    logger.info("id=" + item.getId() + ", name: " + item.getName());
                }
                return department;
            }
        }
        return null;
    }

    public List<WechatUser> getUserListByDepId(String accessToken, int department_id, String fetch_child, String status) {
        String getUserList = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=" + accessToken;
        String getDepartmentUsers = getUserList + "&department_id=" + department_id + "&fetch_child=" + fetch_child + "&status=" + status;
        String departUserList = UrlUtil.urlPost(getDepartmentUsers, "");
        if (StringUtils.isNotBlank(departUserList)) {
            WechatUserMsg userMsg = GsonUtil.getInstance().fromJson(departUserList, WechatUserMsg.class);
            if (userMsg != null && userMsg.getErrcode() == 0 && userMsg.getUserlist() != null) {
                List<WechatUser> userList = userMsg.getUserlist();
                for (WechatUser depUser : userList) {
                    String userId = depUser.getUserid();
                    String userName = depUser.getName();
                    logger.info("department_id=" + department_id + ", userId=" + userId + ", userName=" + userName);
                }
                return userList;
            }
        }
        return null;
    }

    public List<WechatUser> getAllCorpUser(String accessToken) {
        List<DepartmentDetail> departmentList = getDepartmentList(accessToken);
        if(departmentList == null || departmentList.isEmpty()) {
            return null;
        }

        List<WechatUser> allCorpUser = Lists.newArrayList();
        for (DepartmentDetail item : departmentList) {
            int department_id = item.getId();
            List<WechatUser> depUserList = getUserListByDepId(accessToken, department_id, "1", "0");
            if(depUserList != null && !depUserList.isEmpty()) {
                allCorpUser.addAll(depUserList);
            }
        }
        return allCorpUser;
    }



}
