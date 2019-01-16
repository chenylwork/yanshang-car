package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Activity
 * @Description 活动实体
 * @Author 陈彦磊
 * @Date 2019/1/16- 16:46
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_activity")
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityid; // 活动编号
    private String title; // 活动标题
    private String content; // 活动内容
    private String image; // 活动头像
    private String start; // 开始时间
    private String end; // 结束时间

}
