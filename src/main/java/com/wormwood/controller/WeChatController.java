package com.wormwood.controller;

/**
 * User: tangbin
 * Date: 2017/7/25
 * T me: 8:35
 * Description: To change this template use File | Settings | File Templates.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.wormwood.DTO.*;
import com.wormwood.client.WechatClient;
import com.wormwood.response.Response;
import com.wormwood.result.ResultEnum;
import com.wormwood.service.WechatService;
import com.wormwood.util.GsonUtil;
import com.wormwood.util.UrlUtil;
import com.wormwood.vo.WechatToken;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class WeChatController {

    private static Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Value("${conf.corp.id}")
    private String corpid;

    @Value("${conf.corp.secret}")
    private String corpsecret;

    @Autowired
    private WechatService wechatService;

    @RequestMapping("/sendMsgPage")
    public String toSendMsgPage() {
        logger.info("toSendMsgPage wechatService: " + wechatService.findByCorpid(corpid));
        return "wechat/sendMsgPage";
    }

    @Autowired
    private WechatClient wechatClient;

    @RequestMapping("/updateCorpDetail")
    public @ResponseBody
    ResponseEntity updateCorpDetail(String corpid, String corpsecret, int agentid) throws Exception {
        GmsCorpDTO dbData = wechatService.findByCorpid(corpid);
        if (dbData == null) {
            GmsCorpDTO newData = new GmsCorpDTO();
            newData.setCorpid(corpid);
            newData.setCorpsecret(corpsecret);
            newData.setAgentid(agentid);
            newData.setCrtBy("SYS");
            newData.setCrtDate(new Date());
            wechatService.insertNewCorp(newData);

            return new Response(ResultEnum.INSERT_SUCCESS).build();
            //  insert data
        } else {
            //
            dbData.setCorpsecret(corpsecret);
            dbData.setAgentid(agentid);
            dbData.setUdpBy("SYS");
            dbData.setUpdDate(new Date());
            wechatService.updateCorp(dbData);

            return new Response(ResultEnum.UPDATE_SUCCESS).build();
        }
    }

    @RequestMapping("/sendTextMessage")
    public @ResponseBody
    String sendTextMessage(HttpServletRequest request, String corpid, String touser, String toparty, String content, String safe) throws Exception {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        GmsCorpDTO dbData = wechatService.findByCorpid(corpid);
        if (dbData == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1102 ");
            returnMap.put("message ", "Can not find corpid[" + corpid + "] in our database , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        if (dbData.getCorpsecret() == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1102 ");
            returnMap.put("message ", " corpsecret for [" + corpid + "]  is null , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        try {
            String dbCorpSecret = dbData.getCorpsecret();
            int dbAgentId = dbData.getAgentid();

            logger.info("sendTextMessage corpid:  " + corpid + ", dbCorpSecret: " + dbCorpSecret + ", dbAgentId: " + dbAgentId + ", wechatClient:" + wechatClient);
            logger.info("sendTextMessage touser: " + touser + ", toparty: " + toparty + ", content: " + content + ", safe=" + safe);

            TextMessage textMessage = new TextMessage();
            textMessage.setTouser(touser);
            textMessage.setTotag(null);
            textMessage.setAgentid(dbAgentId);
            Map<String, String> textMap = Maps.newHashMap();
            textMap.put("content", content);
            textMessage.setText(textMap);

            String accessToken = wechatService.getAccessToken(corpid, dbCorpSecret);
            if (accessToken == null) {
                returnMap.put("success", false);
                returnMap.put("code ", "1102 ");
                returnMap.put("message ", "Can not get access token from weichat server, Please call updateCorpDetail!");
                return new Gson().toJson(returnMap).toString();
            }

            List<DepartmentDetail> department = wechatService.getDepartmentList(accessToken);
            if(department == null || department.isEmpty()) {
                returnMap.put("success", false);
                returnMap.put("code ", "1102 ");
                returnMap.put("message ", "Can not find department by input toparty=" + toparty);
                return new Gson().toJson(returnMap).toString();
            }

            for (DepartmentDetail item : department) {
                logger.info("id=" + item.getId() + ", name: " + item.getName());
                if (toparty.equalsIgnoreCase(item.getName())) {
                    textMessage.setToparty(item.getId() + "");
                    String mesgContent = GsonUtil.getInstance().toJson(textMessage);
                    logger.info("sendTextMessage mesgContent: " + mesgContent);

                    String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
                    String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
                    logger.info("sendTextMessage returnMsg: " + returnMsg);
                }
            }

            returnMap.put("success", true);
            returnMap.put("code ", "1101 ");
            returnMap.put("message ", "Send Text Message Successful.");

        } catch (Exception ex) {
            returnMap.put("success", false);
            returnMap.put("code ", "1102 ");
            returnMap.put("message ", "Send Text Message Failure.");
        }

        return new Gson().toJson(returnMap).toString();
    }

    @RequestMapping("/sendImageMessage")
    public @ResponseBody
    String sendImageMessage(@RequestParam(value = "file", required = false) MultipartFile image, String toparty, HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        GmsCorpDTO dbData = wechatService.findByCorpid(corpid);
        if (dbData == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", "Can not find corpid[" + corpid + "] in our database , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        if (dbData.getCorpsecret() == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", " corpsecret for [" + corpid + "]  is null , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        try {
            String dbCorpSecret = dbData.getCorpsecret();
            int dbAgentId = dbData.getAgentid();
            logger.info("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

            String filenameame = image.getOriginalFilename();
            String path = request.getSession().getServletContext().getRealPath("upload");

            logger.info("MultipartFile: " + image.getBytes().length + ", fileName: " + filenameame + ", path: " + path);
            File tempFile = new File(path);
            logger.info("MultipartFile: " + tempFile.exists());

            WechatToken wechatToken = wechatClient.getToken(corpid, dbCorpSecret);
            logger.info("sendImageMessage wechatToken:" + wechatToken.getAccess_token() + ",   " + wechatToken.getExpires_in());

            String accessToken = wechatToken.getAccess_token();
            logger.info("sendImageMessage accessToken:" + accessToken);

            String media_id = UrlUtil.uploadFileBytes(filenameame, image.getBytes(), accessToken, "image");
            String getDepartList = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + accessToken;
            String departList = UrlUtil.urlPost(getDepartList, "");
            logger.info("sendImageMessage departList: " + departList);
            if (StringUtils.isNotBlank(departList)) {
                DepartmentMsg departmentMsg = GsonUtil.getInstance().fromJson(departList, DepartmentMsg.class);
                if (departmentMsg != null) {
                    List<DepartmentDetail> department = departmentMsg.getDepartment();
                    for (DepartmentDetail item : department) {
                        logger.info("id=" + item.getId() + ", name: " + item.getName());
                        if (toparty.equalsIgnoreCase(item.getName())) {

                            String imageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=image";
                            Map<String, Object> messageMap = Maps.newHashMap();
                            messageMap.put("touser", null);
                            messageMap.put("toparty", item.getId());
                            messageMap.put("totag", null);
                            messageMap.put("msgtype", "image");
                            messageMap.put("agentid", 0);
                            messageMap.put("safe", 0);

                            Map<String, String> imageMap = Maps.newHashMap();
                            imageMap.put("media_id", media_id);
                            messageMap.put("image", imageMap);

                            String mesgContent = GsonUtil.getInstance().toJson(messageMap);
                            logger.info("mesgContent: " + mesgContent);

                            String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
                            String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
                            logger.info("returnMsg: " + returnMsg);
                        }
                    }
                }
                returnMap.put("success", true);
                returnMap.put("code ", "1301  ");
                returnMap.put("message ", "Send Image Message Successful.");
            }
        } catch (Exception ex) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", "Send Image Message Failure.");
        }

        return new Gson().toJson(returnMap).toString();
    }

    @RequestMapping("/sendFileMessage")
    public @ResponseBody
    String sendFileMessage(@RequestParam(value = "file", required = false) MultipartFile file, String departmentName, HttpServletRequest request) throws Exception {
        logger.info("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);
        Map<String, Object> returnMap = new HashMap<String, Object>();

        GmsCorpDTO dbData = wechatService.findByCorpid(corpid);
        if (dbData == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", "Can not find corpid[" + corpid + "] in our database , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        if (dbData.getCorpsecret() == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", " corpsecret for [" + corpid + "]  is null , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }
        String dbCorpSecret = dbData.getCorpsecret();
        int dbAgentId = dbData.getAgentid();
        logger.info("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

        WechatToken wechatToken = wechatClient.getToken(corpid, dbCorpSecret);
        logger.info("sendFileMessage wechatToken:" + wechatToken.getAccess_token() + ",   " + wechatToken.getExpires_in());

        String accessToken = wechatToken.getAccess_token();
        logger.info("sendFileMessage accessToken:" + accessToken);

        String filenameame = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("upload");

        logger.info("sendFileMessage MultipartFile: " + file.getBytes().length + ", fileName: " + filenameame + ", path: " + path);
        File tempFile = new File(path);
        logger.info("sendFileMessage MultipartFile: " + tempFile.exists());


        String media_id = UrlUtil.uploadFileBytes(filenameame, file.getBytes(), accessToken, "image");

        String imageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=image";
        Map<String, Object> messageMap = Maps.newHashMap();
        messageMap.put("touser", null);
        messageMap.put("toparty", 1);
        messageMap.put("totag", null);
        messageMap.put("msgtype", "file");
        messageMap.put("agentid", 0);
        messageMap.put("safe", 0);

        Map<String, String> imageMap = Maps.newHashMap();
        imageMap.put("media_id", media_id);
        messageMap.put("file", imageMap);

        String mesgContent = GsonUtil.getInstance().toJson(messageMap);
        logger.info("sendFileMessage mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        logger.info("sendFileMessage returnMsg: " + returnMsg);

        return null;
    }

    @RequestMapping("/sendNewsMessage")
    public @ResponseBody
    String sendNewsMessage(String departmentName) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        GmsCorpDTO dbData = wechatService.findByCorpid(corpid);
        if (dbData == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", "Can not find corpid[" + corpid + "] in our database , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        if (dbData.getCorpsecret() == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", " corpsecret for [" + corpid + "]  is null , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }
        String dbCorpSecret = dbData.getCorpsecret();
        int dbAgentId = dbData.getAgentid();
        logger.info("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

        WechatToken wechatToken = wechatClient.getToken(corpid, dbCorpSecret);
        logger.info("sendNewsMessage wechatToken:" + wechatToken.getAccess_token() + ",   " + wechatToken.getExpires_in());

        String accessToken = wechatToken.getAccess_token();
        logger.info("sendNewsMessage accessToken:" + accessToken);


        String imageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=image";
        Map<String, Object> messageMap = Maps.newHashMap();
        messageMap.put("touser", null);
        messageMap.put("toparty", 1);
        messageMap.put("totag", null);
        messageMap.put("msgtype", "news");
        messageMap.put("agentid", 0);
        messageMap.put("safe", 0);

        //  articles start
        List<Map<String, String>> articleList = Lists.newArrayList();

        Map<String, String> newsItem_01 = Maps.newHashMap();
        newsItem_01.put("title", "程序员五大搞笑瞬间，你中了几枪？");
        newsItem_01.put("description", "good morning");
        newsItem_01.put("url", "http://www.toutiao.com/group/6418073560187207938/");
        newsItem_01.put("picurl", "http://p1.pstatp.com/list/190x124/1bf70017069ca1c2755c");
        articleList.add(newsItem_01);

        Map<String, String> newsItem_02 = Maps.newHashMap();
        newsItem_02.put("title", "比诺基亚还经典，又一手机巨头回归中国！");
        newsItem_02.put("description", "Hellow World");
        newsItem_02.put("url", "http://www.toutiao.com/group/6444855895029825806/");
        newsItem_02.put("picurl", "http://p3.pstatp.com/list/190x124/3e4000224987647e021");
        articleList.add(newsItem_02);

        Map<String, String> newsItem_03 = Maps.newHashMap();
        newsItem_03.put("title", "国家出手！4G/宽带费用暴降玩真的");
        newsItem_03.put("description", "Hellow World");
        newsItem_03.put("url", "http://www.toutiao.com/group/6446628850491228429/");
        newsItem_03.put("picurl", "http://p1.pstatp.com/list/190x124/30fc00002ea3f9cba941");
        articleList.add(newsItem_03);

        Map<String, String> newsItem_04 = Maps.newHashMap();
        newsItem_04.put("title", "Windows干掉了内置画图软件，微软你对力量一无所知");
        newsItem_04.put("description", "Hellow World");
        newsItem_04.put("url", "http://www.toutiao.com/group/6446698296010424590/");
        newsItem_04.put("picurl", "http://p3.pstatp.com/list/190x124/2c6e001daca270d3ed20");
        articleList.add(newsItem_04);

        Map<String, List<Map<String, String>>> articles = Maps.newHashMap();
        articles.put("articles", articleList);

        //  articles end

        messageMap.put("news", articles);

        String mesgContent = GsonUtil.getInstance().toJson(messageMap);
        logger.info("sendNewsMessage  mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        logger.info("sendNewsMessage  returnMsg: " + returnMsg);

        return null;
    }


    @RequestMapping("/sendMpnewsMessage")
    public @ResponseBody
    String sendMpnewsMessage(@RequestParam(value = "file", required = false) MultipartFile file, String departmentName, HttpServletRequest request) throws Exception {
        logger.info("sendMpnewsMessage corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);
        Map<String, Object> returnMap = new HashMap<String, Object>();

        GmsCorpDTO dbData = wechatService.findByCorpid(corpid);
        if (dbData == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", "Can not find corpid[" + corpid + "] in our database , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }

        if (dbData.getCorpsecret() == null) {
            returnMap.put("success", false);
            returnMap.put("code ", "1302 ");
            returnMap.put("message ", " corpsecret for [" + corpid + "]  is null , Please call updateCorpDetail!");
            return new Gson().toJson(returnMap).toString();
        }
        String dbCorpSecret = dbData.getCorpsecret();
        int dbAgentId = dbData.getAgentid();
        logger.info("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

        WechatToken wechatToken = wechatClient.getToken(corpid, dbCorpSecret);
        logger.info("sendMpnewsMessage wechatToken:" + wechatToken.getAccess_token() + ",   " + wechatToken.getExpires_in());

        String accessToken = wechatToken.getAccess_token();
        logger.info("sendMpnewsMessage accessToken:" + accessToken);

        String filenameame = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("upload");

        logger.info("sendMpnewsMessage MultipartFile: " + file.getBytes().length + ", fileName: " + filenameame + ", path: " + path);
        File tempFile = new File(path);
        logger.info("sendMpnewsMessage MultipartFile: " + tempFile.exists());

        String media_id = UrlUtil.uploadFileBytes(filenameame, file.getBytes(), accessToken, "image");
        logger.info("sendMpnewsMessage media_id:  " + media_id);

        //  articles start
        List<Map<String, String>> articleList = Lists.newArrayList();

        Map<String, String> newsItem_01 = Maps.newHashMap();
        newsItem_01.put("title", "Mpnews Test");
        newsItem_01.put("thumb_media_id", media_id);
        newsItem_01.put("author", "tangbin");
        newsItem_01.put("content_source_url", "http://www.toutiao.com/a6431492433566400770/");
        newsItem_01.put("content", "H</br>e</br>llo");
        newsItem_01.put("digest", "Digest description");
        newsItem_01.put("show_cover_pic", "0");

        articleList.add(newsItem_01);

        Map<String, List<Map<String, String>>> articles = Maps.newHashMap();
        articles.put("articles", articleList);

        //  articles end
        Map<String, Object> messageMap = Maps.newHashMap();
        messageMap.put("touser", null);
        messageMap.put("toparty", 1);
        messageMap.put("totag", null);
        messageMap.put("msgtype", "mpnews");
        messageMap.put("agentid", 0);
        messageMap.put("safe", 0);
        messageMap.put("mpnews", articles);

        String mesgContent = GsonUtil.getInstance().toJson(messageMap);
        logger.info("sendMpnewsMessage  mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        logger.info("sendMpnewsMessage  returnMsg: " + returnMsg);

        return null;
    }

}
