package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName OnceOrder
 * @Description 全款订单表
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:34
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_once")
@AllArgsConstructor
@NoArgsConstructor
public class OnceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderid; // 订单编号
}
