package com.yanshang.car.controller;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.ServerService;
import com.yanshang.car.sms.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    /**
     * 保存活动
     * @param activity
     * @return
     */
    @RequestMapping("/activity/save")
    public NetMessage saveActivity(Activity activity, MultipartFile... file){
        return serverService.saveActivity(activity,file);
    }

    /**
     * 用户加入活动
     * @param activity
     * @return
     */
    @RequestMapping("/activity/init")
    public NetMessage initActivity(AccountActivity activity){
        return serverService.initActivity(activity);
    }

    /**
     * 获取活动信息
     * 如果有活动编号将根据活动编号获取单条活动信息
     * 如果有用户编号将获取该用户参与的所有活动信息（用户-活动视图）
     * 没有查询参数（size=0）将获取全部活动信息
     * @param data
     * @return
     */
    @RequestMapping("/activity/get")
    public NetMessage getActivity(@RequestParam Map<String,String> data){
        return serverService.getActivity(data);
    }

    @RequestMapping("/inform/save")
    public NetMessage saveInform(Inform inform) {
        return serverService.saveInform(inform);
    }
    @RequestMapping("/inform/get")
    public NetMessage getInform(Map<String,String> data) {
        return serverService.getInform(data);
    }
    @RequestMapping("/inform/read")
    public NetMessage readInform(@RequestParam Map<String,String> data){
        return serverService.readInform(data);
    }
    @RequestMapping("/notice/save")
    public NetMessage saveNotice(Notice notice) {
        return serverService.saveNotice(notice);
    }
    @RequestMapping("/notice/get")
    public NetMessage getNotice(Map<String,String> data) {
        return serverService.getNotice(data);
    }
    /**
     * 用户读取系统公告
     * @param data
     * @return
     */
    @RequestMapping("/notice/read")
    public NetMessage readNotice(@RequestParam Map<String,String> data){
        return serverService.readNotice(data);
    }

    /**
     * 生成试驾汽车订单号
     * @return
     */
    @RequestMapping("/car/test/order/code")
    public NetMessage getCarTestOrderCode() {
        String code = CharacterUtil.orderCode();
        return NetMessage.successNetMessage("","SJ"+code);
    }
    /**
     * 生成租车订单号
     * @return
     */
    @RequestMapping("/car/rent/order/code")
    public NetMessage getCarRentOrderCode() {
        String code = CharacterUtil.orderCode();
        return NetMessage.successNetMessage("","ZC"+code);
    }
    /**
     * 生成租车订单号
     * @return
     */
    @RequestMapping("/shop/order/code")
    public NetMessage getShopOrderCode() {
        String code = CharacterUtil.orderCode();
        return NetMessage.successNetMessage("","SP"+code);
    }
    @Autowired
    private ServerService serverService;
}
