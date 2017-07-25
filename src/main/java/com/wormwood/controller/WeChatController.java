package com.wormwood.controller;

/**
 * Created by Donnie on 2017/2/17.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat")
public class WeChatController {


    @RequestMapping("/sendMsgPage")
    public String toProjectList() {
        return "wechat/sendMsgPage";
    }


    @RequestMapping("/sendMsgOut")
    public @ResponseBody String sendWechat(String msgTextarea) throws Exception {
        System.out.println("========sgTextarea: " + msgTextarea);

        /*String accessToken = UrlUtil.getAccessToken();
        MpNewsMsg msg = new MpNewsMsg();

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
        return UrlUtil.urlPost(sendUrl, jsonContent);*/
        return null;
    }



}
