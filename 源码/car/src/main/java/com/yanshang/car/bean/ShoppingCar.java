package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName ShoppingCar
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/16- 17:05
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_shoppingcar")
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shoppingid; // 编号
    private String commodityid; // 商品编号
    private int size; // 商品个数
    private String accountid; // 用户编号

}
