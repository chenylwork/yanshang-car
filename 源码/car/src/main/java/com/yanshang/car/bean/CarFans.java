package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName CarFans
 * @Description 关注的汽车
 * @Author 陈彦磊
 * @Date 2019/2/12- 17:04
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_fans")
@AllArgsConstructor
@NoArgsConstructor
public class CarFans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fansid; // 编号
    private String accountid; // 用户编号
    private String carid; // 汽车编号
}
