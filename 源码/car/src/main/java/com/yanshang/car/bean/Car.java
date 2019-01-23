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
    private String name; // 汽车名称：2018款帝豪GSE
    private String brand; // 汽车品牌：吉利汽车
    private String series; // 汽车系列：帝豪
    private String model; // 汽车型号：帝豪GSE
    private String deploy; // 汽车配置：标配、高配。{配置编号:配置名称,配置编号:配置名称}
    private String colors; // 汽车颜色
    private String images; // 汽车图片。汽车编号+[汽车颜色]
    private String label; // 汽车标签：主打车，特价车。。。
}
