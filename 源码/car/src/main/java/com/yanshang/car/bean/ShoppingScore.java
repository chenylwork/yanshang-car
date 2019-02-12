package com.yanshang.car.bean;

/*
 * @ClassName ShoppingScore
 * @Description 商品积分明细
 * @Author 陈彦磊
 * @Date 2019/2/12- 9:01
 * @Version 1.0
 **/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="t_shop_score")
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreid; // id编号
    private String accountid; // 用户编号
    private String name; // 积分获取名称，签到、消费
    private int score; // 分数。正数为获取的，负数为消费的
    private String time; // 时间
}
