package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName ShoppingCart
 * @Description 商品购物车
 * @Author 陈彦磊
 * @Date 2019/1/28- 9:30
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_shopping_cart")
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartid; // 编号
    private String commodityid; // 商品编号
    private int size; // 商品个数
    private String accountid; // 用户编号
    private String time; // 加入时间
}
