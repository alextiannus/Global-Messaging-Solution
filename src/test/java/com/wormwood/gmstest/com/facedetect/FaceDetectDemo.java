package com.wormwood.gmstest.com.facedetect;

import com.alibaba.fastjson.JSONObject;
import com.wormwood.util.UrlUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class FaceDetectDemo {

    private static final String CONTENT_CHARSET = "UTF-8";
    private static final String appKey = "8ce61298967ec2f58f862b3183835bb5";
    private static final String secret = "8947dac31722413b90fdadb60e23def8";
    private static String faceDir = "D:\\appl\\data\\facedetect\\";

    private static String uri = "/ai-cloud-face/face/tool/compare";

    @Test
    public void testFaceDetect() {

        try {
            Map<Object, Object> parameters = new HashMap<>();
            String nonceStr = "11223456";
            parameters.put("appKey", appKey);
            parameters.put("nonceStr", nonceStr);
            parameters.put("img", Base64.getEncoder().encodeToString(getFaceByte("timg.jpg")));
            parameters.put("sign", createSign(parameters));

            //HttpUtil.post("https://api-ai.cloudwalk.cn" + uri, JSONObject.toJSONString(parameters));
            String responseContent = UrlUtil.urlPost("https://api-ai.cloudwalk.cn" + uri, JSONObject.toJSONString(parameters));
            System.out.println(responseContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getFaceByte(String fileName) throws Exception {
        File file = new File(faceDir + fileName);
        return IOUtils.toByteArray(new FileInputStream(file));
    }

    private static String createSign(Map<Object, Object> parameters) throws Exception {
        SortedMap<Object, Object> parametersForSign = new TreeMap<>();
        parametersForSign.put("uri", uri);
        parametersForSign.putAll(parameters);
        StringBuilder sb = new StringBuilder();
        Set es = parametersForSign.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                if (!it.hasNext()) {
                    sb.append(k).append("=").append(v);
                } else {
                    sb.append(k).append("=").append(v).append("&");
                }
            }
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secretKey);
        byte[] hash = mac.doFinal(sb.toString().getBytes(CONTENT_CHARSET));
        return new BASE64Encoder().encode(hash);
    }

}
