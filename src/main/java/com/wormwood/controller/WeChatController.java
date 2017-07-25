package com.wormwood.controller;

/**
 * Created by Donnie on 2017/2/17.
 */

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
    String sendWechat(String departmentName, String msgTextarea) throws Exception {
        System.out.println("corpid:  " + corpid + ", corpsecret: " + corpsecret + ", wechatClient: " + wechatClient);
        System.out.println("departmentName: " + departmentName + ", sgTextarea: " + msgTextarea);

        WechatToken wechatToken = wechatClient.getToken(corpid, corpsecret);
        System.out.println("wechatToken:" + wechatToken);


        String accessToken = UrlUtil.getAccessToken();
        System.out.println("accessToken:" + accessToken);

        TextMessage textMessage = new TextMessage();
        textMessage.setTouser(null);
        textMessage.setTotag(null);
        textMessage.setAgentid(0);
        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("content", msgTextarea);
        textMessage.setText(textMap);

        String getDepartList = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=" + accessToken;
        String departList = UrlUtil.urlPost(getDepartList, "");
        System.out.println("departList: " + departList);
        if (StringUtils.isNotBlank(departList)) {
            DepartmentMsg departmentMsg = GsonUtil.getInstance().fromJson(departList, DepartmentMsg.class);
            if (departmentMsg != null) {
                List<DepartmentDetail> department = departmentMsg.getDepartment();
                for (DepartmentDetail item : department) {
                    System.out.println("id=" + item.getId() + ", name: " + item.getName());
                    if (departmentName.equalsIgnoreCase(item.getName())) {
                        textMessage.setToparty(item.getId() + "");
                        String mesgContent = GsonUtil.getInstance().toJson(textMessage);
                        System.out.println("mesgContent: " + mesgContent);

                        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
                        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
                        System.out.println("returnMsg: " + returnMsg);
                    }
                }
            }
        }




        /*MpNewsMsg msg = new MpNewsMsg();

        Article article = new Article("2kfb8gad2m8Tv9KpPgmeZ60ND0nYSMOp0jZxmFvyAi_BMZo-ILfryiGQsIZKAzNq2","【外盘日讯】 特朗普演说反应正面 ：美联储3月加息机率暴增至66.4%");//
        article.setContent(Constants.content);
        article.setDigest("digest");
        article.setShow_cover_pic(0);

        MpNews mpNews = new MpNews();
        List<Article> articleList = new ArrayList<Article>();
        articleList.add(article);
        mpNews.setArticles(articleList);

        msg.setTouser("@all");
        msg.setMsgtype("mpNews");
        msg.setAgentid(0);
        msg.setMpnews(mpNews);

        String jsonContent = gsonHtml.toJson(msg);
        String sendUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        log.debug("--------------------------jsonMsg:{}", jsonContent);
        return UrlUtil.urlPost(sendUrl, jsonContent);
        */

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
        System.out.println("MultipartFile: " + tempFile.exists() );

        String accessToken = UrlUtil.getAccessToken();
        System.out.println("accessToken:" + accessToken);

        String media_id = UrlUtil.uploadFileBytes(filenameame, file.getBytes(),  accessToken, "image");

        String imageUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=" + accessToken + "&type=image";
        Map<String, Object> messageMap = new HashMap<String, Object>();
        messageMap.put("touser", null);
        messageMap.put("toparty", 1);
        messageMap.put("totag", null);
        messageMap.put("msgtype", "image");
        messageMap.put("agentid", 0);
        messageMap.put("safe", 0);

        Map<String, String> imageMap = new HashMap<String, String>();
        imageMap.put("media_id", media_id);
        messageMap.put("image", imageMap);

        String mesgContent = GsonUtil.getInstance().toJson(messageMap);
        System.out.println("mesgContent: " + mesgContent);

        String textMessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + accessToken;
        String returnMsg = UrlUtil.urlPost(textMessageUrl, mesgContent);
        System.out.println("returnMsg: " + returnMsg);

        return null;
    }

}
