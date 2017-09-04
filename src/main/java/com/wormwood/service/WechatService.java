package com.wormwood.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.wormwood.DTO.*;
import com.wormwood.dao.WechatDao;
import com.wormwood.util.GsonUtil;
import com.wormwood.util.UrlUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: tangbin
 * Date: 2017/7/27
 * T me: 8:35
 * Description: use wechat tables
 */
@Service(value="wechatService")
public class WechatService {

    private static Logger logger = LoggerFactory.getLogger(WechatService.class);

    @Value("${conf.corp.id}")
    private String corpid;

    @Value("${conf.corp.secret}")
    private String corpsecret;

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

    /**
     * Get all corp department
     *
     * @param accessToken
     * @return
     */
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

    /**
     * get all corp user by department id
     *
     * @param accessToken
     * @param department_id
     * @param fetch_child
     * @param status
     * @return
     */
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
                    String weixinid = depUser.getWeixinid();
                    logger.info("department_id=" + department_id + ", userId=" + userId + ", userName=" + userName + ", weixinid=" + weixinid);
                }
                return userList;
            }
        }
        return null;
    }


    /**
     * get all corp user
     *
     * @param accessToken
     * @return
     */
    public List<WechatUser> getAllCorpUser(String accessToken) {
        if (accessToken == null) {
            accessToken = getAccessToken();
        }

        List<DepartmentDetail> departmentList = getDepartmentList(accessToken);
        if (departmentList == null || departmentList.isEmpty()) {
            return null;
        }

        List<WechatUser> allCorpUser = Lists.newArrayList();
        for (DepartmentDetail item : departmentList) {
            int department_id = item.getId();
            List<WechatUser> depUserList = getUserListByDepId(accessToken, department_id, "1", "0");
            if (depUserList != null && !depUserList.isEmpty()) {
                allCorpUser.addAll(depUserList);
            }
        }
        return allCorpUser;
    }

    public List<WechatUser> getUserIdsByWechat(String weixinIds) {
        if (weixinIds == null || StringUtils.isBlank(weixinIds)) {
            return null;
        }

        List<WechatUser> resultList = Lists.newArrayList();

        List<WechatUser> allCorpUser = getAllCorpUser(null);

        Map<String, Boolean> helpMap = Maps.newHashMap();

        String[] weixinIdArr = weixinIds.split("|");
        for (String weixinId : weixinIdArr) {
            helpMap.put(weixinId, Boolean.TRUE);
        }

        for(WechatUser user:allCorpUser) {
            String weixinId = user.getWeixinid();
            if(helpMap.get(weixinId) != null) {
                resultList.add(user);
            }
        }
        return null;
    }


    public String getAccessToken() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret;
        String tokenJsonStr = UrlUtil.urlGet(url);
        JsonObject jsonObject = GsonUtil.getInstance().fromJson(tokenJsonStr, JsonObject.class);
        if (jsonObject != null) {
            return jsonObject.get("access_token").getAsString();
        }
        return null;
    }

    public static void main(String args[]) {
        try {
            WechatService wechatService = new WechatService();
            wechatService.getAllCorpUser(null);
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
    }

}
