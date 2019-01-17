package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName CarSeries
 * @Description 汽车系列：属于汽车品牌下层
 * @Author 陈彦磊
 * @Date 2019/1/17- 11:47
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_comment")
@AllArgsConstructor
@NoArgsConstructor
public class CarSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seriesid; // 系列编号
    private String name; // 系列名称
}
