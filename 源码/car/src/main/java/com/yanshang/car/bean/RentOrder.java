package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName RentOrder
 * @Description 租车订单
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:46
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_rent")
@AllArgsConstructor
@NoArgsConstructor
public class RentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderid; // 订单编号
    private String userid; // 用户编号
    private String carid; // 汽车编号
    private String color; //  汽车颜色
    private String deploy; // 汽车配置
    private String name; // 租客名称
    private String time; // 租车时间
    private String merchant; // 商家信息
    private String phone; // 电话
    private String address; // 地址
}
