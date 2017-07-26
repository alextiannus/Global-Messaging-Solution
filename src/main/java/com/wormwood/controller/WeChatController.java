package com.wormwood.controller;

/**
 * Created by Donnie on 2017/2/17.
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wormwood.DTO.DepartmentDetail;
import com.wormwood.DTO.DepartmentMsg;
import com.wormwood.DTO.TextMessage;
import com.wormwood.client.WechatClient;
import com.wormwood.util.GsonUtil;
import com.wormwood.util.UrlUtil;
import com.wormwood.vo.WechatToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class WeChatController {

    @Value("${dingding.corpid}")
    private String corpid;

    @Value("${dignding.corpsecret}")
    private String corpsecret;

    @RequestMapping("/sendMsgPage")
    public String toProjectList() {
        return "wechat/sendMsgPage";
    }

    @Autowired
    private WechatClient wechatClient;

    @RequestMapping("/sendTextMessage")
    public @ResponseBody
    String sendTextMessage(String departmentName, String msgTextarea) throws Exception {
        System.out.println("sendTextMessage corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);
        System.out.println("sendTextMessage departmentName: " + departmentName + ", sgTextarea: " + msgTextarea);

        WechatToken wechatToken = wechatClient.getToken(corpid, corpsecret);
        System.out.println("sendTextMessage wechatToken:" + wechatToken);


        String accessToken = UrlUtil.getAccessToken();
        System.out.println("sendTextMessage accessToken:" + accessToken);

        TextMessage textMessage = new TextMessage();
        textMessage.setTouser(null);
        textMessage.setTotag(null);
        textMessage.setAgentid(0);
        Map<String, String> textMap = Maps.newHashMap();
        textMap.put("content", msgTextarea);
        textMessage.setText(textMap);

        String getDepartList = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + accessToken;
        String departList = UrlUtil.urlPost(getDepartList, "");
        System.out.println("sendTextMessage departList: " + departList);
        if (StringUtils.isNotBlank(departList)) {
            DepartmentMsg departmentMsg = GsonUtil.getInstance().fromJson(departList, DepartmentMsg.class);
            if (departmentMsg != null) {
                List<DepartmentDetail> department = departmentMsg.getDepartment();
                for (DepartmentDetail item : department) {
                    System.out.println("id=" + item.getId() + ", name: " + item.getName());
                    if (departmentName.equalsIgnoreCase(item.getName())) {
                        textMessage.setToparty(item.getId() + "");
                        String mesgContent = GsonUtil.getInstance().toJson(textMessage);
                        System.out.println("sendTextMessage mesgContent: " + mesgContent);

                        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
                        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
                        System.out.println("sendTextMessage returnMsg: " + returnMsg);
                    }
                }
            }
        }

        return null;
    }

    @RequestMapping("/sendImageMessage")
    public @ResponseBody
    String sendImageMessage(@RequestParam(value = "file", required = false) MultipartFile file, String departmentName, HttpServletRequest request) throws Exception {
        System.out.println("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

        String filenameame = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("upload");

        System.out.println("MultipartFile: " + file.getBytes().length + ", fileName: " + filenameame + ", path: " + path);
        File tempFile = new File(path);
        System.out.println("MultipartFile: " + tempFile.exists());

        String accessToken = UrlUtil.getAccessToken();
        System.out.println("accessToken:" + accessToken);

        String media_id = UrlUtil.uploadFileBytes(filenameame, file.getBytes(), accessToken, "image");

        String imageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=image";
        Map<String, Object> messageMap = Maps.newHashMap();
        messageMap.put("touser", null);
        messageMap.put("toparty", 1);
        messageMap.put("totag", null);
        messageMap.put("msgtype", "image");
        messageMap.put("agentid", 0);
        messageMap.put("safe", 0);

        Map<String, String> imageMap = Maps.newHashMap();
        imageMap.put("media_id", media_id);
        messageMap.put("image", imageMap);

        String mesgContent = GsonUtil.getInstance().toJson(messageMap);
        System.out.println("mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        System.out.println("returnMsg: " + returnMsg);

        return null;
    }

    @RequestMapping("/sendFileMessage")
    public @ResponseBody
    String sendFileMessage(@RequestParam(value = "file", required = false) MultipartFile file, String departmentName, HttpServletRequest request) throws Exception {
        System.out.println("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

        String filenameame = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("upload");

        System.out.println("sendFileMessage MultipartFile: " + file.getBytes().length + ", fileName: " + filenameame + ", path: " + path);
        File tempFile = new File(path);
        System.out.println("sendFileMessage MultipartFile: " + tempFile.exists());

        String accessToken = UrlUtil.getAccessToken();
        System.out.println("sendFileMessage accessToken:" + accessToken);

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
        System.out.println("sendFileMessage mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        System.out.println("sendFileMessage returnMsg: " + returnMsg);

        return null;
    }

    @RequestMapping("/sendNewsMessage")
    public @ResponseBody
    String sendNewsMessage(String departmentName) throws Exception {

        System.out.println("sendNewsMessage corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);
        String accessToken = UrlUtil.getAccessToken();
        System.out.println("accessToken:" + accessToken);


        String imageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=image";
        Map<String, Object> messageMap = Maps.newHashMap();
        messageMap.put("touser", null);
        messageMap.put("toparty", 1);
        messageMap.put("totag", null);
        messageMap.put("msgtype", "news");
        messageMap.put("agentid", 0);
        messageMap.put("safe", 0);

        //  articles start
        List<Map<String, String>> articleList= Lists.newArrayList();

        Map<String, String> newsItem_01 = Maps.newHashMap();
        newsItem_01.put("title", "华为美国并购得与失");
        newsItem_01.put("description", "good morning");
        newsItem_01.put("url", "http://www.toutiao.com/group/6446536895971295502/");
        newsItem_01.put("picurl", "http://p3.pstatp.com/list/190x124/2f930006686e9f0279d3");
        articleList.add(newsItem_01);

        Map<String, String> newsItem_02 = Maps.newHashMap();
        newsItem_02.put("title", "国家出手！4G/宽带费用暴降玩真的");
        newsItem_02.put("description", "Hellow World");
        newsItem_02.put("url", "http://www.toutiao.com/group/6446628850491228429/");
        newsItem_02.put("picurl", "http://p1.pstatp.com/list/190x124/30fc00002ea3f9cba941");
        articleList.add(newsItem_02);

        Map<String, List<Map<String, String>>> articles =Maps.newHashMap();
        articles.put("articles", articleList);

        //  articles end

        messageMap.put("news", articles);

        String mesgContent = GsonUtil.getInstance().toJson(messageMap);
        System.out.println("sendNewsMessage  mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        System.out.println("sendNewsMessage  returnMsg: " + returnMsg);

        return null;
    }

}
