package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName News
 * @Description 发现模块的新闻
 * @Author 陈彦磊
 * @Date 2019/1/16- 16:28
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_news")
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsid; // 编号
    private String title; // 标题
    private String content; // 内容
    private String userid; // 用户编号
    private String time; // 发布时间
    private int showCount = 0; // 查看次数
    private int praise = 0; // 点赞个数
    private int comment = 0; // 评论个数
}
