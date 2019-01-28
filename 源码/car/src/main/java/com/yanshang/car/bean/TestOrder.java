package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName TestOrder
 * @Description 试驾订单
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:46
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_test")
@AllArgsConstructor
@NoArgsConstructor
public class TestOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderid; // 订单编号
    private String userid; // 用户编号
    private String carid; // 汽车编号
    private String color; //  汽车颜色
    private String deploy; // 汽车配置
    private String time;// 时间
    private String address; // 地点
    private String name; // 业务员姓名
    private String phone; // 业务员电话
    private String message; // 给业务员留言
}
