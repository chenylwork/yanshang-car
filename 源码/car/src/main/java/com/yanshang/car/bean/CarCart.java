package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName ShoppingCar
 * @Description 汽车购物车实体
 * @Author 陈彦磊
 * @Date 2019/1/16- 17:05
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_cart")
@AllArgsConstructor
@NoArgsConstructor
public class CarCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartid; // 编号
    private String carid; // 汽车编号
    private int size; // 商品个数
    private String userid; // 用户编号
    private String time; // 加入时间
}
