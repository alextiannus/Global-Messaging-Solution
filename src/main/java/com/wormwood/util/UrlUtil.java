package com.wormwood.util;

import com.google.gson.JsonObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by Donnie on 2017/2/20.
 */
@Component
public class UrlUtil {

    private static Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    private static String CORPID;    // = "wx3a42b774b7b91ccf";
    private static String APPSECRET;// = "tpfikag8WOgdhafho3-cEgqJVQwTN3daf-u9182mUbVT4H-uHsTqYUye7uk6Acnr";
    private static String ACCESS_TOKEN_URL;// = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx3a42b774b7b91ccf&corpsecret=tpfikag8WOgdhafho3-cEgqJVQwTN3daf-u9182mUbVT4H-uHsTqYUye7uk6Acnr";
    private static String UPLOAD_URL;// = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    @Value("${conf.corp.id}")
    public void setCORPID(String id) {
        CORPID = id;
    }

    @Value("${conf.corp.secret}")
    public void setAPPSECRET(String secret) {
        APPSECRET = secret;
    }

    @Value("${conf.url.gettoken}")
    public void setAccessTokenUrl(String tokenUrl) {
        ACCESS_TOKEN_URL = tokenUrl;
    }

    @Value("${conf.url.upload}")
    public void setUploadUrl(String uploadUrl) {
        UPLOAD_URL = uploadUrl;
    }

    protected static Logger log = LoggerFactory.getLogger(UrlUtil.class);

    public static String urlGet(String urlStr) {
        StringBuilder json = new StringBuilder();
        BufferedReader in = null;
        try {
            URL url = new URL(urlStr);
            System.out.println("urlGet: " + url);
            URLConnection conn = url.openConnection();
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
        } catch (MalformedURLException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        log.debug("------url get result:{}", json);
        return json.toString();
    }

    public static String urlPost(String urlStr, String param) {
        StringBuilder response = new StringBuilder();
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            param = param.replace("", "");
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            //post method
            conn.setDoOutput(true);
            conn.setDoInput(true);

//            out = new PrintWriter(conn.getOutputStream());
            //jie jue luan ma
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            out.print(param);
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        log.debug("------json post result:{}", response);
        return response.toString();
    }

    public static String upload(String filePath, String accessToken, String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("file not exist ");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JsonObject jsonObject = GsonUtil.getInstance().fromJson(result, JsonObject.class);
        System.out.println(jsonObject);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObject.get(typeName).getAsString();
        return mediaId;
    }

    public static String uploadFileBytes(String filename, byte[] data, String accessToken, String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + filename + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        out.write(data);

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JsonObject jsonObject = GsonUtil.getInstance().fromJson(result, JsonObject.class);
        System.out.println("uploadFileBytes" + jsonObject);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObject.get(typeName).getAsString();
        return mediaId;
    }

    public static String getAccessToken() {
        String url = ACCESS_TOKEN_URL.replace("APPID", CORPID).replace("APPSECRET", APPSECRET);
        String tokenJsonStr = urlGet(url);
        JsonObject jsonObject = GsonUtil.getInstance().fromJson(tokenJsonStr, JsonObject.class);
        if (jsonObject != null) {
            return jsonObject.get("access_token").getAsString();
        }
        log.error("------get access token failed!");
        return "get token failed!";
    }

    //standard post method, more standard than urlPost
    public static String urlPost2(String urlStr, String param) {
        StringBuilder response = new StringBuilder();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //post method
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            log.error(e.toString());
        }
        log.debug("------json post result:{}", response);
        return response.toString();
    }

    public static String uploadFile(String actionUrl, byte[] data, String filename) throws Exception {

        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            URL url = new URL(actionUrl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file \";filename=\"" + filename + "\"" + end);
            ds.writeBytes(end);
            ds.write(data);
            ds.writeBytes(end);

            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            ds.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return resultBuffer.toString();
        }
    }

}
