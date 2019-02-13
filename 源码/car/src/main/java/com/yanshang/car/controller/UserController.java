package com.yanshang.car.controller;

import com.yanshang.car.bean.Fans;
import com.yanshang.car.bean.Idea;
import com.yanshang.car.bean.Recommend;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
 * @ClassName UserController
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/12- 14:39
 * @Version 1.0
 **/
@RestController
public class UserController {
    /**
     * 关注用户
     * @param fans
     * @return
     */
    @RequestMapping("/fans/save")
    public NetMessage saveFans(Fans fans) {
        return accountService.saveFans(fans);
    }

    /**
     * 获取我关注的集合
     * @param accountid
     * @return
     */
    @RequestMapping("/fans/get/to")
    public NetMessage getFansTo(String accountid) {
        return accountService.getFans(accountid,AccountService.FANS_TO);
    }

    /**
     * 获取我的粉丝集合
     * @param accountid
     * @return
     */
    @RequestMapping("/fans/get/from")
    public NetMessage getFansFrom(String accountid) {
        return accountService.getFans(accountid,AccountService.FANS_FROM);
    }
    /**
     * 获取我关注的个数
     * @param accountid
     * @return
     */
    @RequestMapping("/fans/len/to")
    public NetMessage getFansToLen(String accountid) {
        return accountService.getFansSize(accountid,AccountService.FANS_TO);
    }

    /**
     * 获取我的粉丝个数
     * @param accountid
     * @return
     */
    @RequestMapping("/fans/len/from")
    public NetMessage getFansFromLen(String accountid) {
        return accountService.getFansSize(accountid,AccountService.FANS_FROM);
    }

    /**
     * 保存反馈意见
     * @param idea
     * @return
     */
    @RequestMapping("/idea/save")
    public NetMessage saveIdea(Idea idea){
        return accountService.saveIdea(idea);
    }

    /**
     * 获取反馈意见
     * @param ideaid
     * @return
     */
    @RequestMapping("/idea/get")
    public NetMessage getIdea(String ideaid){
        return accountService.getIdea(ideaid);
    }

    /**
     * 保存推荐信息
     * @param recommend
     * @return
     */
    @RequestMapping("/recommend/save")
    public NetMessage saveRecommend(Recommend recommend){
        return accountService.saveRecommend(recommend);
    }

    /**
     * 获取推荐信息
     * @param data
     * @return
     */
    @RequestMapping("/recommend/get")
    public NetMessage getRecommend(@RequestParam Map<String,String> data){
        return accountService.getRecommend(data);
    }
    @Autowired
    private AccountService accountService;
}
