package com.yanshang.car.services.impl;

import com.yanshang.car.bean.Account;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.commons.PhoneCodeUtil;
import com.yanshang.car.repositories.AccountRepository;
import com.yanshang.car.services.AccountService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

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
        if (!CharacterUtil.checkPhone(account.getPhone())) {
            return NetMessage.failNetMessage("","请输入正确的手机号！！");
        }
        if (CharacterUtil.isEmpty(account.getPassword())) {
            return NetMessage.failNetMessage("","缺少密码！！");
        }
        if (code != null && !code.equals("")) {
            NetMessage netMessage = PhoneCodeUtil.checkCode(account.getPhone(), code);
            if (netMessage.getStatus() == NetMessage.FAIl) {
                return netMessage;
            }
        }
        account.setPassword(CharacterUtil.passEncode(account.getPassword()));
        try{
            accountRepository.save(account);
            return NetMessage.successNetMessage("","注册成功！！");
        } catch (DataIntegrityViolationException exception){
            return NetMessage.failNetMessage("","该账户已注册！！");
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
        Account account = accountRepository.getByPhoneAndPassword(phone, CharacterUtil.passEncode(password));
        if (account != null && account.getId() != null) {
            return NetMessage.successNetMessage("","登录成功");
        } else {
            return NetMessage.successNetMessage("","输入的信息有误！！");
        }
    }

    @Override
    public NetMessage sendCode(String phone) {
        return PhoneCodeUtil.sendCode(phone);
    }

    @Override
    public NetMessage checkCode(String phone, String code) {
        return PhoneCodeUtil.checkCode(phone,code);
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

    @Override
    public NetMessage getUser(String userid) {
        if (userid == null || "".equals(userid)) return NetMessage.failNetMessage("","缺少用户编号！！");
        Optional<Account> byId = accountRepository.findById(Integer.parseInt(userid));
        if (!byId.isPresent()) return NetMessage.failNetMessage("","暂无该用户");
        return NetMessage.successNetMessage("",byId.get());
    }
}
