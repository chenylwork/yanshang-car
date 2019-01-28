package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Employee
 * @Description 业务员集合信息
 * @Author 陈彦磊
 * @Date 2019/1/28- 15:25
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeid; // 业务员编号
    private String name; // 名称
    private String phone; // 电话
    private String sex; // 性别
    private String head; // 头像
    private String worktime; // 工作年限（经验）


}
