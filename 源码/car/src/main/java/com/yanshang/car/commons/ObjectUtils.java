package com.yanshang.car.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/*
 * @ClassName ObjectUtils
 * @Description 对象操作
 * @Author 陈彦磊
 * @Date 2019/1/23- 9:36
 * @Version 1.0
 **/
public class ObjectUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换为json字符串
     * @param object
     * @return
     */
    public static String object2Json(Object object) {
        String result = "";
        if (object == null) return "";
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static JsonNode string2JsonNode(String data) {
        try {
            return objectMapper.readTree(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 对象转换为map
     * @param object
     * @return
     */
    public static HashMap<String,Object> object2Map(Object object) {
        if (object == null) return null;
        try {
            String string = objectMapper.writeValueAsString(object);
            return objectMapper.readValue(string, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转换为map对象
     * @param data
     * @return
     */
    public static HashMap<String,Object> string2Map(String data) {
        if (CharacterUtil.isEmpty(data)) return null;
        try {
            return objectMapper.readValue(data,HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 读取string为对象
     * @param data
     * @param classEntity
     * @param <T>
     * @return
     */
    public static <T> T string2Object(String data,Class<T> classEntity) {
        if (CharacterUtil.isEmpty(data)) return null;
        try {
            return objectMapper.readValue(data,classEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
