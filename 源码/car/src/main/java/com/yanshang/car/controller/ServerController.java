package com.yanshang.car.controller;

import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.sms.SMSUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @ClassName ServerController
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/18- 13:24
 * @Version 1.0
 **/
@RestController
@RequestMapping("/server")
public class ServerController {

    /**
     * 短信激活ip
     * @return
     */
    @RequestMapping("/trust")
    public NetMessage SmsTrust() {
        return SMSUtil.trust();
    }
}
