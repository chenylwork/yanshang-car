package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName TestOrder
 * @Description 试驾订单
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:46
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_test")
@AllArgsConstructor
@NoArgsConstructor
public class TestOrder {
    @Id
    private String ordercode; // 订单编号
    private String userid; // 用户编号
    private String carid; // 汽车编号
    private String image; //  汽车图片
    private String model; // 汽车配置：基础版
    private String time;// 预约时间时间
    private String address; // 地点
//    private String name; // 业务员姓名
//    private String phone; // 业务员电话
//    private String message; // 给业务员留言
    private String createTime; // 订单创建时间
    private String status = "0"; // 订单状态：0未完成，1已完成


}
