package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName CommodityOrder
 * @Description 商品购买订单
 * @Author 陈彦磊
 * @Date 2019/1/16- 17:14
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_commodity")
@AllArgsConstructor
@NoArgsConstructor
public class CommodityOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderid; // 订单编号
    private Integer orderCode; // 订单号
    private String createTime; // 创建时间
    private String paymentTime; // 付款时间
    private String sendTime; // 发货时间
    private String endTime; // 完成时间
    // 订单详细信息：[
    // {商品编号:商品编号,购买价格:购买价格,购买个数:购买个数},
    // {商品编号:商品编号,购买价格:购买价格,购买个数:购买个数},
    // ,....]
    private String content;

}
