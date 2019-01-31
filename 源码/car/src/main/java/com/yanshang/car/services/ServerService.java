package com.yanshang.car.services;

import com.yanshang.car.commons.NetMessage;

import java.util.HashMap;
import java.util.Map;

/*
 * @ClassName ServerService
 * @Description 服务操作
 * @Author 陈彦磊
 * @Date 2019/1/31- 17:18
 * @Version 1.0
 **/
public interface ServerService {
    /**
     * 获取字典集合
     * @param data
     * @return
     */
    NetMessage codes(Map<String,String> data);
}
