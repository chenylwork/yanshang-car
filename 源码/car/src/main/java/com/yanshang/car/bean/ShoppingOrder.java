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
@Table(name="t_order_shop")
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingOrder {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderCode; // 订单号
    private String accountid; // 用户编号
    private String goodsid; // 商品编号
    @Column(name = "money", columnDefinition = "decimal(10,2)")
    private Double money; // 总价
    @Column(name = "paymoney",columnDefinition = "decimal(10,2)")
    private Double paymoney; // 支付金额
    private int score; // 使用积分
    private String createTime; // 创建时间
    private String paymentTime; // 付款时间
    private String sendTime; // 发货时间
    private String endTime; // 完成时间
    // 订单详细信息：[
    // {商品编号:商品编号,购买价格:购买价格,购买个数:购买个数},
    // {商品编号:商品编号,购买价格:购买价格,购买个数:购买个数},
    // ,....]
    private Integer size;

}
