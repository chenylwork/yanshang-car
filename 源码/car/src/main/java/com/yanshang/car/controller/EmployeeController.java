package com.yanshang.car.controller;

import com.yanshang.car.bean.Employee;
import com.yanshang.car.bean.Reserve;
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
     * 预约上门服务
     * @param reserve 预约信息对象
     * @return
     */
    @RequestMapping("/reserve")
    public NetMessage reserve(Reserve reserve) {
        return null;
    }

    @Autowired
    private EmployeeService employeeService;


}
