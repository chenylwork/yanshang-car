package com.yanshang.car.services.impl;

import com.yanshang.car.bean.Account;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.commons.PhoneCodeUtil;
import com.yanshang.car.repositories.AccountRepository;
import com.yanshang.car.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @ClassName AccountServiceImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 15:44
 * @Version 1.0
 **/
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public NetMessage register(Account account, String code) {
        if (code != null && !code.equals("")) {
            if (!PhoneCodeUtil.checkCode(account.getPhone(),code)) {
                return NetMessage.failNetMessage("","验证码错误！！");
            }
        }
        if (!CharacterUtil.checkPhone(account.getPhone())) {
            return NetMessage.failNetMessage("","请输入正确的手机号！！");
        }
        account.setPassword(CharacterUtil.passEncode(account.getPassword()));
        try{
            accountRepository.save(account);
            return NetMessage.successNetMessage("","注册成功！！");
        } catch (Exception e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
    }

    @Override
    public NetMessage referrer(String phone, String name, String referrerPhone) {
        if (phone == null || phone.equals("")) return NetMessage.failNetMessage("","需要用户手机号参数！！");
        Account account = accountRepository.getByPhone(phone);
        if (account != null || account.getId() == null) return NetMessage.failNetMessage("","该账号不存在！！");
        account.setReferrerName(name);
        account.setReferrerPhone(referrerPhone);
        account = accountRepository.save(account);
        try{
            accountRepository.save(account);
            return NetMessage.successNetMessage("","推荐人录入成功！！");
        } catch (Exception e) {
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
    }

    @Override
    public NetMessage login(String phone, String password) {
        if (CharacterUtil.isEmpty(phone) || CharacterUtil.isEmpty(password)){
            return NetMessage.failNetMessage("","缺少参数！！");
        }
        Account account = accountRepository.getByPhoneaAndPassword(phone, CharacterUtil.passEncode(password));
        if (account != null && account.getId() != null) {
            return NetMessage.successNetMessage("","登录成功");
        } else {
            return NetMessage.successNetMessage("","输入的信息有误！！");
        }
    }

    @Override
    public NetMessage sendCode(String phone) {
        String code = PhoneCodeUtil.sendCode(phone);
        if (!"".equals(code)) {
            return NetMessage.successNetMessage("",code);
        } else {
            return NetMessage.failNetMessage("","手机号格式不正确！！");
        }
    }

    @Override
    public NetMessage checkCode(String phone, String code) {
        if (PhoneCodeUtil.checkCode(phone,code)) {
            return NetMessage.successNetMessage("","验证成功！！");
        }
        return NetMessage.failNetMessage("","验证失败！！");
    }

    @Override
    public NetMessage changePass(String phone, String password) {
        if (CharacterUtil.isEmpty(phone)) return NetMessage.failNetMessage("","需要手机号参数！！");
        Account account = accountRepository.getByPhone(phone);
        if (account == null || account.getId() == null) {
            return NetMessage.failNetMessage("","该账户还未注册！！");
        }
        try {
            accountRepository.save(account);
        } catch (Exception e){
            e.printStackTrace();
            return NetMessage.errorNetMessage();
        }
        return NetMessage.successNetMessage("","密码修改成功！！");
    }
}
