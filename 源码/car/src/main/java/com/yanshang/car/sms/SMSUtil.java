package com.yanshang.car.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.FileUtil;
import com.yanshang.car.commons.NetMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wuwenguan
 * Date:2018/4/20
 **/
@Slf4j
@Component
public class SMSUtil {

    private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);
    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        SMSUtil.redisTemplate = redisTemplate;
    }

    private static final String SMS_KEY = DigestUtils.sha512Hex("sms_sign");

    /**
     * 发送短信
     *
     * @param content
     * @param phone
     * @return
     */
    public static NetMessage send(String content, String phone) {

        ObjectMapper objectMapper = new ObjectMapper();
        // 请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", MD5Util.md5Crypt(password));
        requestBody.put("content", content);
        requestBody.put("phone", phone);



        Map<String, Object> requestHead = new HashMap<>();
        requestHead.put("appid",appid);
        requestHead.put("apiver",apiver);
        String timestamp = System.currentTimeMillis()+"";
        requestHead.put("timestamp",timestamp);
        String nz = "0";
        requestHead.put("nz",nz);
        String crypt = "0";
        requestHead.put("crypt",crypt);


        String sign = SignUtil.getSign(appKey, requestBody, requestHead);
        requestHead.put("sign", sign);
        requestBody.put("sign", SIGN);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(SEND_URL);

        httpPost.addHeader("appid",(String) requestHead.get("appid"));
        httpPost.addHeader("apiver",(String) requestHead.get("apiver"));
        httpPost.addHeader("timestamp",(String) requestHead.get("timestamp"));
        httpPost.addHeader("sign",(String) requestHead.get("sign"));
        httpPost.addHeader("nz",(String) requestHead.get("nz"));
        httpPost.addHeader("crypt",(String) requestHead.get("crypt"));
        httpPost.setHeader("Accept","aplication/json");
        httpPost.addHeader("Content-Type","application/json;charset=UTF-8");
//        PostMethod method = new PostMethod(SEND_URL);
//        method.addRequestHeader("appid",appid);
//        method.addRequestHeader("apiver",apiver);
//        method.addRequestHeader("timestamp",System.currentTimeMillis()+"");
//        method.addRequestHeader("sign",sign);
//        method.addRequestHeader("nz",nz);
//        method.addRequestHeader("crypt",crypt);
        String responseBody = "";
        CloseableHttpResponse response = null;
        try {
            String requestContent = objectMapper.writeValueAsString(requestBody);
            String charset = "utf-8";
            String contentType = "application/json";
            StringEntity entity = new StringEntity(requestContent,"utf-8");
            System.out.println(requestContent);
            entity.setContentEncoding(charset);
            entity.setContentType(contentType);
            httpPost.setEntity(entity);
//            method.setRequestEntity(new StringRequestEntity(requestContent, contentType, charset));
//            int code = httpClient.execute(method);
//            int statusCode = response.getStatusLine().getStatusCode();
//            responseBody = method.getResponseBodyAsString();
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            int code = response.getStatusLine().getStatusCode();
//            HttpEntity responseEntity = response.getEntity();
//            responseBody = EntityUtils.toString(responseEntity, "utf8");
            Map<String,String> responseHeads = new HashMap<>();
            for (Header header : response.getAllHeaders()) {
                responseHeads.put(header.getName(),header.getValue());
            }

            if (code == 200) {
                if (responseHeads.containsKey("nz") && "1".equals(responseHeads.get("nz"))) {
                    InputStream is = responseEntity.getContent();
                    is= new GZIPInputStream(is);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while((line = br.readLine())!=null) {
                        sb.append(line);
                    }
                    responseBody = sb.toString();
                }
                // 返回值解密
                if (responseHeads.containsKey("crypt")){
                    String responseCrypt = responseHeads.get("crypt");
                    // AES128 解密 ,密钥为appSecret的前16bytes
                    if ("1".equals(responseCrypt)) {

                    }
                    // AES256 解密,密钥为appSecret
                    if ("2".equals(responseCrypt)) {

                    }
                }
                if (CharacterUtil.isEmpty(responseBody)) {
                    responseBody = EntityUtils.toString(responseEntity, "utf-8");
                }
                HashMap<String, Object> hashMap = objectMapper.readValue(responseBody, HashMap.class);
                int status = (Integer) hashMap.get("status");
                if (90001 == status) {
                    return NetMessage.successNetMessage("", hashMap);
                } else {
                    return NetMessage.failNetMessage("", hashMap);
                }
            } else {
                return NetMessage.failNetMessage("", responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return NetMessage.failNetMessage("",responseBody);
        } finally {
            logger.info(responseBody);
            try {

                response.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                return NetMessage.errorNetMessage();
            }

        }
    }

    public static NetMessage trust() {
        Map<String, Object> requestHead = new HashMap<>();
        requestHead.put("appid",appid);
        requestHead.put("apiver",apiver);
        String timestamp = System.currentTimeMillis()+"";
        requestHead.put("timestamp",timestamp);
        String nz = "0";
        requestHead.put("nz",nz);
        String crypt = "0";
        requestHead.put("crypt",crypt);



        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(SERVER_URL);
        postMethod.addRequestHeader("appid",appid);
        postMethod.addRequestHeader("apiver",apiver);
        postMethod.addRequestHeader("timestamp",System.currentTimeMillis()+"");
//        postMethod.addRequestHeader("sign",sign);
        postMethod.addRequestHeader("nz","0");
        postMethod.addRequestHeader("crypt","0");
//        postMethod.addRequestHeader("content-type","application/json");
        try {
            postMethod.setRequestEntity(new StringRequestEntity("","application/json","UTF-8"));
            int i = httpClient.executeMethod(postMethod);
            if (i == 200) {
                log.info("本服务器成功加入，短信发送白名单");
                return NetMessage.successNetMessage("","激活成功！！");
            } else {
                log.info("本服务器加入短信发送白名单,失败！！");
                HashMap hashMap = new ObjectMapper().readValue(postMethod.getResponseBodyAsString(), HashMap.class);
                log.info("失败原因："+hashMap);
                return NetMessage.failNetMessage("",hashMap);
            }
        }catch (Exception e) {
            log.info("本服务器加入短信发送白名单,出现异常！！！");
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }

    }

    private static String getSign(Map<String,String> requestBody,Map<String, Object> requestHead) {
//        String sign = redisTemplate.opsForValue().get(SMS_KEY);
        String sign = SignUtil.getSign(appKey, requestBody, requestHead);

//        if (sign == null || "".equals(sign) || "null".equals(sign)) {
//            //假设接口请求头参数如下
//            HashMap<String, String> headers = new HashMap<String, String>();
//            headers.put("appid", appid);
//            headers.put("apiver", "101");
//            headers.put("timestamp", System.currentTimeMillis() + "");
//            headers.put("nz", "0");
//            headers.put("crypt", "0");
//            headers.put("sign", "b9bf825d8c99ffe1216e24b61a9a8b41");
//
//            //假设请求内容如下
//            TestBean testBean = new TestBean();
//            testBean.setEntId("0");
//            testBean.setId("10000");
//            testBean.setTestArray(new ArrayList<>());
//
//            TreeMap<String, Object> treeMap = new TreeMap<>();
//            // treeMap中添加appid,apiver
//            treeMap.put("appid", appid);
//            treeMap.put("apiver", apiver);
//            //添加请求头中的参数(sign属性除外)
//            treeMap.put("timestamp", headers.get("timestamp"));
//            treeMap.put("crypt", headers.get("crypt"));
//            treeMap.put("nz", headers.get("nz"));
//
//            //对象、集合不参与签名 去除body中的对象
//            Map<String, Object> bodyMap = null;
//            try {
//                bodyMap = transBean2Map(testBean);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //添加请求内容
//            treeMap.putAll(bodyMap);
//            //根据Treemap及signKey 生成签名
//            String keyValues = SignUtil.treeMapToString(treeMap, signKey);
//            sign = MD5Util.md5Crypt(keyValues);
//            redisTemplate.opsForValue().set(SMS_KEY, sign);
//        }
//        System.out.println("sign:" + sign);
        return sign;
    }

    private static Map<String, Object> transBean2Map(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (!"class".equals(key)) {
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                if ("String".equals(value.getClass().getSimpleName()) || value.getClass().isPrimitive()) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    private static String username;
    private static String password;

    @Value("${sms.username}")
    public void setUsername(String username) {
        this.username = username;
    }

    @Value("${sms.pass}")
    public void setPassword(String password) {
        this.password = password;
    }

    private final static String SERVER_URL = "https://www.020ymkj.com/sendSms/";
    private final static String SEND_URL = "https://www.020ymkj.com/sendSms/sendMessage";
//    appId:HEBEICHUANGGEE5XEQFLF1NW7W0YE1J2
//    appKey:OP9UGHZWRC3L72H7O3MHRVRA7RL0PIP6
//    appSecret:eSjnSzZJNltJzKjphVhowg==
    private final static String SIGN = "河北格创";
    /**
     * 身份标识id
     */
    private final static String appid = "HEBEICHUANGGEE5XEQFLF1NW7W0YE1J2";
    /**
     * 身份标识Key
     */
    private final static String appKey = "OP9UGHZWRC3L72H7O3MHRVRA7RL0PIP6";
    /**
     * 接口密钥 appSecret
     */
    private final static String appSecrect = "eSjnSzZJNltJzKjphVhowg==";
    /**
     * 接口协议版本 此接口规范固定为101
     */
    private final static String apiver = "101";
    /**
     * 签名密钥 通过拼接 appKey + appSecret(仅获取后10位) 取得
     */
    private final static String signKey = appKey + appSecrect.substring(appSecrect.length() - 10, appSecrect.length());

}
