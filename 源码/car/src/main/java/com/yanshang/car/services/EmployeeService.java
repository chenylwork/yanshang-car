package com.yanshang.car.services;

import com.yanshang.car.bean.Employee;
import com.yanshang.car.commons.NetMessage;

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
}
