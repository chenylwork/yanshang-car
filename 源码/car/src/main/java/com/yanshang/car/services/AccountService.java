package com.yanshang.car.services;

import com.yanshang.car.bean.Account;
import com.yanshang.car.commons.NetMessage;
import sun.nio.ch.Net;

/*
 * @ClassName AccountService
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 15:43
 * @Version 1.0
 **/
public interface AccountService {
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
}
