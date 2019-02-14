package com.yanshang.car.repositories;

import com.yanshang.car.bean.ReserveOrder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

/*
 * @ClassName ReserveOrderRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/14- 17:58
 * @Version 1.0
 **/
public interface ReserveOrderRepository
        extends JpaRepositoryImplementation<ReserveOrder,Integer>{

    List<ReserveOrder> getByAccountid(String accountid);
}
