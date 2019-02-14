package com.yanshang.car.controller;

import com.yanshang.car.bean.Employee;
import com.yanshang.car.bean.ReserveOrder;
import com.yanshang.car.commons.MPage;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @ClassName EmployeeController
 * @Description 员工管理
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:04
 * @Version 1.0
 **/
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /**
     * 获取所有业务员
     * @return
     */
    @RequestMapping("/salesmen/get")
    public NetMessage salesmen(Employee employee) {
        return employeeService.getSalesmen(employee);
    }
    @RequestMapping("/salesmen/save")
    public NetMessage salesman(Employee employee) {
        return employeeService.saveSalesmen(employee);
    }

    /**
     * 保存上门服务订单
     * @param reserve 预约信息对象
     * @return
     */
    @RequestMapping("/reserve/save")
    public NetMessage saveReserve(ReserveOrder reserve) {
        return employeeService.saveReserve(reserve);
    }

    /**
     * 获取上门服务订单信息
     * @param reserve
     * @return
     */
    @RequestMapping("/reserve/get")
    public NetMessage getReserve(ReserveOrder reserve,MPage<ReserveOrder> page) {
        return employeeService.getReserve(reserve,page);
    }
    @Autowired
    private EmployeeService employeeService;


}
