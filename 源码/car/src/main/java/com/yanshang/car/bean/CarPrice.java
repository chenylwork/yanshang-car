package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName CarPrice
 * @Description 汽车价钱数据映射实体
 * @Author 陈彦磊
 * @Date 2019/2/14- 10:05
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_price")
@AllArgsConstructor
@NoArgsConstructor
public class CarPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priceid; // 编号
    private String carid; // 汽车编号
    private double price; // 裸车价钱
    private double insurance; // 商业保险

}
