package com.yanshang.car.services;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.NetMessage;
import sun.nio.ch.Net;

import java.util.Map;

/*
 * @ClassName AccountService
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 15:43
 * @Version 1.0
 **/
public interface AccountService {
    /**
     * 推荐码存储编号
     */
    String REFERRER_CODE_NUM_KEY = "referrer_code_num";
    String FANS_TO = "1";
    String FANS_FROM = "2";
    /**
     * 注册账号
     * @param account 注册账户
     * @param code 手机验证码：不为空是验证，若为空时不进行验证
     * @return
     */
    NetMessage register(Account account, String code);

    /**
     * 录入用户推荐人信息
     * @param phone 用户电话
     * @param name 推荐人姓名
     * @param referrerPhone 推荐人电话
     * @return
     */
    NetMessage referrer(String phone,String name,String referrerPhone);

    /**
     * 用户登录
     * @param phone 登录账户手机号
     * @param password 登录密码
     * @return
     */
    NetMessage login(String phone,String password);

    /**
     * 发送验证码
     * @param phone 发送手机号，需要验证
     * @return
     */
    NetMessage sendCode(String phone);

    /**
     * 检验验证码
     * @param phone 验证手机号
     * @param code 验证码
     * @return
     */
    NetMessage checkCode(String phone,String code);

    /**
     * 修改账户密码
     * @param phone
     * @param password
     * @return
     */
    NetMessage changePass(String phone,String password);

    /**
     * 根据用户编号获取用户信息
     * @param userid
     * @return
     */
    NetMessage getUser(String userid);

    /**
     * 保存粉丝，关注用户
     * @param fans
     * @return
     */
    NetMessage saveFans(Fans fans);

    /**
     * 获取粉丝（关注）集合
     * @param accountid
     * @return
     */
    NetMessage getFans(String accountid,String type);

    /**
     * 获取粉丝（关注）的个数
     * @param accountid
     * @param type
     * @return
     */
    NetMessage getFansSize(String accountid,String type);

    /**
     * 保存反馈意见
     * @param idea
     * @return
     */
    NetMessage saveIdea(Idea idea);

    /**
     * 获取反馈意见
     * @param ideaid
     * @return
     */
    NetMessage getIdea(String ideaid);

    /**
     * 保存推荐人关系
     * @param recommend
     * @return
     */
    NetMessage saveRecommend(Recommend recommend);

    /**
     * 获取推荐人关系信息
     * @param data
     * @return
     */
    NetMessage getRecommend(Map<String,String> data);


}
