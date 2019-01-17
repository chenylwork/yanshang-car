package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/*
 * @ClassName Car
 * @Description 汽车实体
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:24
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car")
@AllArgsConstructor
@NoArgsConstructor
//@Proxy(lazy = false)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carid; // 汽车编号
    private String brand; // 汽车品牌
    private String series; // 汽车系列
    private String label; // 汽车标签：主打车，特价车。。。
}
