package com.yanshang.car.services;

import com.yanshang.car.bean.Employee;
import com.yanshang.car.bean.ReserveOrder;
import com.yanshang.car.commons.MPage;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @ClassName EmployeeService
 * @Description 员工服务类
 * @Author 陈彦磊
 * @Date 2019/1/28- 15:41
 * @Version 1.0
 **/
public interface EmployeeService {

    /**
     * 保存业务员信息
     * @param employee
     * @return
     */
    NetMessage saveSalesmen(Employee employee);

    /**
     * 获取业务员信息
     * @param employee
     * @return
     */
    NetMessage getSalesmen(Employee employee);
    /**
     * 保存上门服务订单
     * @param reserve 预约信息对象
     * @return
     */
    @RequestMapping("/reserve/save")
    public NetMessage saveReserve(ReserveOrder reserve);

    /**
     * 获取上门服务订单信息
     * @param reserve
     * @return
     */
    @RequestMapping("/reserve/get")
    public NetMessage getReserve(ReserveOrder reserve, MPage<ReserveOrder> page);
}
