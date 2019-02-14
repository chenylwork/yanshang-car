package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @ClassName Order
 * @Description 汽车购买订单
 * @Author 陈彦磊
 * @Date 2019/1/16- 17:45
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_order")
@AllArgsConstructor
@NoArgsConstructor
public class CarOrder {

    @Id
    private String orderCode; // 订单编号
    private String accountid; // 用户编号
    private String carid; // 汽车编号
    private String paytype; // 付款类型：全款，贷款
    private String shoptype; // 购车类型：挂靠。回收
    @Column(name = "money",columnDefinition = "decimal(10,2)")
    private double money; // 裸车价钱，汽车价钱
    @Column(name = "paymoney",columnDefinition = "decimal(10,2)")
    private double paymoney; // 实际已付款
    @Column(name = "onepay",columnDefinition = "decimal(10,2)")
    private String onepay; //  首付价钱
    private int deadline; // 贷款期限
    @Column(name = "rate",columnDefinition = "decimal(10,2)")
    private double rate; // 贷款利率
    @Column(name = "repay",columnDefinition = "decimal(10,2)")
    private double repay; // 每期应还款
    @Column(name = "insurance",columnDefinition = "decimal(10,2)")
    private double insurance; // 商业保险
    private String start; // 开始时间
    private String end; // 结束时间
}
