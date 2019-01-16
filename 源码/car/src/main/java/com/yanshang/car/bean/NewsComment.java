package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Comment
 * @Description 评论信息
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:38
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_news_comment")
@AllArgsConstructor
@NoArgsConstructor
public class NewsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentid; // 评论编号
    private String title; // 评论标题
    private String content; // 评论内容
    private String observer; // 评论人
    private String time; // 评论时间
    private int praise; // 点赞个数
    private int commentCount; // 评论个数
    private int reply; // 回复个数
    private String object; // 评论编号：该字段标识对评论进行回复对话，标识该评论在此字段对应的评论之下
}
