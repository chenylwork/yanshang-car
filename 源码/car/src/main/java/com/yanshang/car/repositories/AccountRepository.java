package com.yanshang.car.repositories;

import com.yanshang.car.bean.Account;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName AccountController
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 15:42
 * @Version 1.0
 **/
@Repository
public interface AccountRepository extends JpaRepositoryImplementation<Account,Integer>{

    /**
     * 根据手机号获取账户信息
     * @param phone
     * @return
     */
    Account getByPhone(String phone);

    /**
     * 根据手机号和密码获取用户信息
     * @param phone 手机号
     * @param password 加密密码
     * @return
     */
    Account getByPhoneaAndPassword(String phone,String password);

}
