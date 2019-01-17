package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Carbrand
 * @Description 汽车品牌
 * @Author 陈彦磊
 * @Date 2019/1/17- 9:41
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_brand")
@AllArgsConstructor
@NoArgsConstructor
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brandid; // 品牌编号
    private String name; // 品牌名称
    private String images; // 品牌图片
}
