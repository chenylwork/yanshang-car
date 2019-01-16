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
}
