package com.yanshang.car.controller;

import com.yanshang.car.bean.News;
import com.yanshang.car.bean.NewsComment;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/*
 * @ClassName FindController
 * @Description 发现与品牌接口
 * @Author 陈彦磊
 * @Date 2019/1/29- 10:38
 * @Version 1.0
 **/
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 添加发现与品牌
     *
     * @param news
     * @return
     */
    @RequestMapping("/save")
    public NetMessage saveNews(News news, MultipartFile... file) {
        return newsService.saveNews(news,file);
    }

    @RequestMapping("/get")
    public NetMessage getNews(HashMap<String,String> data) {
        return newsService.getNews(data);
    }

    /**
     * 添加保存，新闻评论
     *
     * @param newsComment
     * @return
     */
    @RequestMapping("/comment/save")
    public NetMessage saveComment(NewsComment newsComment) {
        return newsService.saveComment(newsComment);
    }

    @RequestMapping("/comment/get")
    public NetMessage getComment(NewsComment newsComment,
                                 @PathVariable(value = "start",required=false)Integer start,
                                 @PathVariable(value = "end",required=false)Integer end) {
        return newsService.getComment(newsComment,start,end);
    }

}
