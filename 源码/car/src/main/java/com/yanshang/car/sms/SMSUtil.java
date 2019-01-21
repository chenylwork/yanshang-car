package com.yanshang.car.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanshang.car.commons.NetMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author wuwenguan
 * Date:2018/4/20
 **/
@Slf4j
@Component
public class SMSUtil {

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        SMSUtil.redisTemplate = redisTemplate;
    }

    private static final String SMS_KEY = DigestUtils.sha512Hex("");

    /**
     * 发送短信
     *
     * @param content
     * @param phone
     * @return
     */
    public static NetMessage send(String content, String phone) {
        String sign = getSign();
        ObjectMapper objectMapper = new ObjectMapper();
        // 请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "ymkj");
        requestBody.put("password", "d93a5def7511da3d0f2d171d9c344e91");
        requestBody.put("content", content);
        requestBody.put("sign", sign);
//        requestBody.put("sendTime",sign);
        requestBody.put("phone", phone);
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(SEND_URL);
        try {
            String requestContent = objectMapper.writeValueAsString(requestBody);
            String charset = "UTF-8";
            String contentType = "application/json";
            method.setRequestEntity(new StringRequestEntity(requestContent, contentType, charset));
            int code = httpClient.executeMethod(method);
            if (code == 200) {
                String responseBody = method.getResponseBodyAsString();
                HashMap<String, Object> hashMap = objectMapper.readValue(responseBody, HashMap.class);
                int status = (Integer) hashMap.get("status");
                if (90001 == status) {
                    return NetMessage.successNetMessage("", hashMap);
                } else {
                    return NetMessage.failNetMessage("", hashMap);
                }
            } else {
                return NetMessage.failNetMessage("", "短信发送请求失败！！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
    }

    public static NetMessage trust() {
        String sign = getSign();
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(SERVER_URL);
        postMethod.addRequestHeader("appid",appid);
        postMethod.addRequestHeader("apiver",apiver);
        postMethod.addRequestHeader("timestamp",System.currentTimeMillis()+"");
        postMethod.addRequestHeader("sign",sign);
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

    private static String getSign() {
        String sign = redisTemplate.opsForValue().get(SMS_KEY);
        if (sign == null || "".equals(sign) || "null".equals(sign)) {
            //假设接口请求头参数如下
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("timestamp", System.currentTimeMillis() + "");
//        0:HTTP Body内容未压缩
//        1:HTTP Body内容通过GZIP方式压缩
            headers.put("nz", "0");
//        0: HTTP Body内容未加密
//        3:HTTP Body内容AES128加密，密钥为appSecret的前16bytes
//        4: HTTP Body 内容AES256加密，密钥为appSecret
            headers.put("crypt", "0");
            headers.put("apiver", "101");
            headers.put("sign", "b9bf825d8c99ffe1216e24b61a9a8b41");

            //假设请求内容如下
            TestBean testBean = new TestBean();
            testBean.setEntId("0");
            testBean.setId("10000");
            testBean.setTestArray(new ArrayList<>());

            TreeMap<String, Object> treeMap = new TreeMap<>();
            // treeMap中添加appid,apiver
            treeMap.put("appid", appid);
            treeMap.put("apiver", apiver);


            //添加请求头中的参数(sign属性除外)
            treeMap.put("timestamp", headers.get("timestamp"));
            treeMap.put("crypt", headers.get("crypt"));
            treeMap.put("nz", headers.get("nz"));

            //对象、集合不参与签名 去除body中的对象
            Map<String, Object> bodyMap = null;
            try {
                bodyMap = transBean2Map(testBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //添加请求内容
            treeMap.putAll(bodyMap);
            //根据Treemap及signKey 生成签名
            String keyValues = SignUtil.treeMapToString(treeMap, signKey);
            sign = MD5Util.md5Crypt(keyValues);
            redisTemplate.opsForValue().set(SMS_KEY, sign);
        }
        System.out.println("sign:" + sign);
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

    private final static String SERVER_URL = "https://www.020ymkj.com/sendSms/";
    private final static String SEND_URL = "https://www.020ymkj.com/sendSms/sendMessage";
//    appId:HEBEICHUANGGEE5XEQFLF1NW7W0YE1J2
//    appKey:OP9UGHZWRC3L72H7O3MHRVRA7RL0PIP6
//    appSecret:eSjnSzZJNltJzKjphVhowg==
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
