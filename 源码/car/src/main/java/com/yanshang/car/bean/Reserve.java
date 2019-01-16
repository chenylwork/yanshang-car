package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Reserve
 * @Description 预约上门服务信息
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:58
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_Reserve")
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reserveid; // 编号
    private String customer; // 客户名称
    private String phone; // 客户名称
    private String address; // 预约地址
    private String time; // 预约地址
    private String status; // 状态

}
