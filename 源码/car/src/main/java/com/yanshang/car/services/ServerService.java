package com.yanshang.car.services;

import com.yanshang.car.bean.AccountActivity;
import com.yanshang.car.bean.Activity;
import com.yanshang.car.bean.Inform;
import com.yanshang.car.bean.Notice;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.multipart.MultipartFile;

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

    String INFORM_VERSION_KEY = "inform_version";
    String NOTICE_VERSION_KEY = "notice_version";
    /**
     * 获取字典集合
     * @param data
     * @return
     */
    NetMessage codes(Map<String,String> data);

    /**
     * 保存活动
     * @param activity
     * @return
     */
    NetMessage saveActivity(Activity activity, MultipartFile... files);

    /**
     * 用户加入活动
     * @param accountActivity
     * @return
     */
    NetMessage initActivity(AccountActivity accountActivity);
    /**
     * 获取活动信息
     * @param data
     * @return
     */
    NetMessage getActivity(Map<String,String> data);
    /**
     * 保存通知信息
     * @param inform
     * @return
     */
    NetMessage saveInform(Inform inform);

    /**
     * 获取通知信息
     * @param data
     * @return
     */
    NetMessage getInform(Map<String,String> data);

    /**
     * 保存公告信息
     * @param notice
     * @return
     */
    NetMessage saveNotice(Notice notice);

    /**
     * 获取公告信息
     * @param data
     * @return
     */
    NetMessage getNotice(Map<String,String> data);

    /**
     * 用户读取系统通知
     * @param data
     * @return
     */
    NetMessage readInform(Map<String,String> data);

    /**
     * 用户读取系统公告
     * @param data
     * @return
     */
    NetMessage readNotice(Map<String,String> data);

}
