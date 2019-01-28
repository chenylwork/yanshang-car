package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName CarComment
 * @Description 汽车评论信息
 * @Author 陈彦磊
 * @Date 2019/1/16- 16:37
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_car_comment")
@AllArgsConstructor
@NoArgsConstructor
public class CarComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentid; // 评论编号
    private String carid; // 评论汽车
    private String observer; // 评论人
    private String title; // 评论标题
    private String content; // 评论内容
    private String time; // 评论时间
}
