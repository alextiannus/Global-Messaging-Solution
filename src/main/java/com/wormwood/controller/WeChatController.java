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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class WeChatController {

    @Value("${conf.corp.id}")
    private String corpid;

    @Value("${conf.corp.secret}")
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
        System.out.println("sendTextMessage wechatToken:" + wechatToken.getAccess_token() + ",   " + wechatToken.getExpires_in());


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
        System.out.println("sendNewsMessage  mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        System.out.println("sendNewsMessage  returnMsg: " + returnMsg);

        return null;
    }


    @RequestMapping("/sendMpnewsMessage")
    public @ResponseBody
    String sendMpnewsMessage(@RequestParam(value = "file", required = false) MultipartFile file, String departmentName, HttpServletRequest request) throws Exception {
        System.out.println("sendMpnewsMessage corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);

        String filenameame = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("upload");

        System.out.println("sendMpnewsMessage MultipartFile: " + file.getBytes().length + ", fileName: " + filenameame + ", path: " + path);
        File tempFile = new File(path);
        System.out.println("sendMpnewsMessage MultipartFile: " + tempFile.exists());

        String accessToken = UrlUtil.getAccessToken();
        System.out.println("sendMpnewsMessage accessToken:" + accessToken);

        String media_id = UrlUtil.uploadFileBytes(filenameame, file.getBytes(), accessToken, "image");
        System.out.println("sendMpnewsMessage media_id:  " + media_id);

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
        System.out.println("sendMpnewsMessage  mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        System.out.println("sendMpnewsMessage  returnMsg: " + returnMsg);

        return null;
    }

}
