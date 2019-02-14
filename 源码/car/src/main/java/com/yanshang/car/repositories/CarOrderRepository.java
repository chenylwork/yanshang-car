package com.yanshang.car.repositories;

import com.yanshang.car.bean.CarOrder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName CarOrderRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/14- 10:56
 * @Version 1.0
 **/
@Repository
public interface CarOrderRepository
    extends JpaRepositoryImplementation<CarOrder,Integer>{
    String paytype_qk = "qk"; // 付款方式-全款
    String paytype_dk = "dk"; // 付款方式-贷款
    String shoptype_gk = "gk"; // 购车方式-挂靠
    String shoptype_hs = "hs"; /// 购车方式-回收

    /**
     * 根据用户编号获取购车信息
     * @param accountid
     * @return
     */
    List<CarOrder> getByAccountid(String accountid);

}
