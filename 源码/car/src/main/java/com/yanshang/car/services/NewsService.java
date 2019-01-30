package com.yanshang.car.services;

import com.yanshang.car.bean.News;
import com.yanshang.car.bean.NewsComment;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * 发现与品牌操作
 */
public interface NewsService {
    /**
     * 保存新闻信息（发表）
     * @param news
     * @param file
     * @return
     */
    NetMessage saveNews(News news,MultipartFile... file);

    /**
     * 获取新闻信息。
     * 如果查询信息有新闻编号：仅仅获取该编号对应的一条新闻信息
     * 否则：分页获取新闻信息
     * @param data
     * @return
     */
    NetMessage getNews(HashMap<String,String> data);

    /**
     * 保存新闻评论
     * @param newsComment
     * @return
     */
    NetMessage saveComment(NewsComment newsComment);

    /**
     * 分页获取获取新闻评论
     * @param data Map对象
     *             有用参数：no：查询页码（可选，从零开始）
     *             end：每页显示记录数（与start参数搭配使用）
     *             newsid：新闻编号
     * @return
     */
    NetMessage getComment(HashMap<String,String> data);

}
