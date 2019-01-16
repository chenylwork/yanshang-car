package com.yanshang.car.controller;

import com.yanshang.car.bean.NewsComment;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @ClassName CommentController
 * @Description 评论请求
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {
    /**
     * 根据被评论对象的唯一标识获取，其对应的所有评论信息
     * @param identity 被评论对象的唯一标识
     * @return
     */
    @RequestMapping("/all")
    public NetMessage comments(String identity) {
        return null;
    }

    /**
     * 根据评论的唯一标识获取该条评论的详细信息
     * @param identity 评论对象的唯一标识
     * @return
     */
    @RequestMapping("/one")
    public NetMessage comment(String identity) {
        return null;
    }

    /**
     * 发表评论
     * @param comment 评论实体对象
     * @return
     */
    @RequestMapping("/issue")
    public NetMessage issue(NewsComment comment) {
        return null;
    }

}
