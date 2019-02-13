package com.yanshang.car.repositories;

import com.yanshang.car.bean.view.AccountActivityView;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName AccountActivityVRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 11:39
 * @Version 1.0
 **/
@Repository
public interface AccountActivityVRepository
        extends JpaRepositoryImplementation<AccountActivityView,Integer>{
    /**
     * 根据用户编号获取其参与的活动
     * @param accountid
     * @return
     */
    List<AccountActivityView> getByAccountid(String accountid);
}
