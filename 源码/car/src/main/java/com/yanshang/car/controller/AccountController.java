package com.yanshang.car.controller;

import com.yanshang.car.bean.Account;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @ClassName AccountController
 * @Description 账号请求处理
 * @Author 陈彦磊
 * @Date 2019/1/4- 16:29
 * @Version 1.0
 **/
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 用户登录
     * @param phone 登录手机号
     * @param password 登录密码
     * @return
     */
    @RequestMapping("/login")
    public NetMessage login(String phone,String password) {
        return accountService.login(phone,password);
    }
    /**
     * 用户注册
     * @param account 注册账户:需要phone注册手机，注册密码参数
     * @param code 手机验证码（如果有验证码就验证没有就不验证）
     * @return
     */
    @RequestMapping("/register")
    public NetMessage register(Account account, String code) {
        return accountService.register(account,code);
    }


    /**
     * 修改登录密码
     * @param phone 修改用户的手机号
     * @param password 修改的新密码
     * @return
     */
    @RequestMapping("/change")
    public NetMessage change(String phone,String password) {
        return accountService.changePass(phone,password);
    }

    /**
     * 录入用户推荐人信息
     * @param phone 用户账户（电话）
     * @param name 推荐人姓名
     * @param name 推荐人电话
     * @return
     */
    @RequestMapping("/referrer")
    public NetMessage initReferrer(String phone,String name,String referrerPhone) {
        return accountService.referrer(phone, name, referrerPhone);
    }

    /**
     * 验证码检验
     * @param phone 手机号
     * @param code 验证码
     * @return
     */
    @RequestMapping("/check")
    public NetMessage checkCode(String phone,String code) {
        return accountService.checkCode(phone, code);
    }

    /**
     * 发送验证码
     * @param phone 发送验证码手机号
     * 需要手机号检验：1、检验是否是手机号
     * @return
     */
    @RequestMapping("/send")
    public NetMessage sendCode(String phone) {
        return accountService.sendCode(phone);
    }

}
