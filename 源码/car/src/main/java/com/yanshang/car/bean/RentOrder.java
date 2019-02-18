package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName RentOrder
 * @Description 租车订单
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:46
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_rent")
@AllArgsConstructor
@NoArgsConstructor
public class RentOrder {
    @Id
    private String ordercode; // 订单编号
    private String userid; // 用户编号
    private String carid; // 汽车编号
    private String image; //  汽车图片
    private String model; // 汽车配置：基础版

    private String price; // 租客价格（/日）
    private String startTime; // 租车开始时间
    private String endTime; // 租车结束时间
    private String day; // 租车时间（天）

    private String address; // 地址
    private String createTime; // 订单创建时间
    private String status = "0"; // 订单状态：0未完成，1已完成
}
