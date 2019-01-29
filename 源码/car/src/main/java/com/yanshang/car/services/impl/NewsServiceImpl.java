package com.yanshang.car.services.impl;

import com.yanshang.car.bean.News;
import com.yanshang.car.bean.NewsComment;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.FileUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.repositories.NewsCommentRepository;
import com.yanshang.car.repositories.NewsRepository;
import com.yanshang.car.services.AccountService;
import com.yanshang.car.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.Net;

import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/*
 * @ClassName NewsServiceImpl
 * @Description 发现与品牌操作
 * @Author 陈彦磊
 * @Date 2019/1/29- 10:41
 * @Version 1.0
 **/
@Service("newsService")
public class NewsServiceImpl implements NewsService {
    @Value("${basic.project.img.home}")
    private String path;
    private String news_img_path = "/news";


    @Override
    public NetMessage saveNews(News news, MultipartFile... file) {
        for (MultipartFile multipartFile : file) {
            if (!FileUtil.isImage(multipartFile)) return NetMessage.failNetMessage("", "上传的文件不是图片！！");
        }
        List<String> list = new ArrayList<>();
        StringBuilder image = new StringBuilder();
        CharacterUtil.dataTime();
        try {
            for (MultipartFile multipartFile : file) {
                String fileName = CharacterUtil.hour()+
                        CharacterUtil.minute()+
                        CharacterUtil.second()+
                        CharacterUtil.millisecond();
                String filePath = CharacterUtil.year().substring(2,4)+"/"+ CharacterUtil.month()+"/"+
                        CharacterUtil.day()+"/"+news.getUserid()+"/"
                        + FileUtil.getFileName(multipartFile,fileName);
                image.append(filePath+";");
                filePath = path + news_img_path +"/"+ filePath;
                list.add(filePath);
                multipartFile.transferTo(FileUtil.createFile(filePath));
            }
            news.setContent(image.toString());
            System.out.println(image.toString());
            news.setTime(CharacterUtil.dataTime());
            newsRepository.save(news);
        } catch (Exception e) {
            e.printStackTrace();
            for (String path : list) FileUtil.removeFile(path);
            System.out.println("删除文件！！");
            return NetMessage.errorNetMessage();
        }
        return NetMessage.successNetMessage("", "发表成功！！");
    }


    @Override
    public NetMessage getNews(HashMap<String,String> data) {
        String newsid = data.get("newsid");
//        Integer newsid = news.getNewsid();
        if (newsid != null) {
            Optional<News> byId = newsRepository.findById(Integer.parseInt(newsid));
            return byId.isPresent() ?
                    NetMessage.successNetMessage("", byId.get()) :
                    NetMessage.failNetMessage("", "没有需要的信息！！");
        }
        String start = data.get("start");
        String end = data.get("end");
        if (start == null || end == null) return NetMessage.failNetMessage("", "需要查询记录区间！！");
        QPageRequest qPageRequest = new QPageRequest(Integer.parseInt(start), Integer.parseInt(end));
        Page<News> page = newsRepository.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.orderBy(criteriaBuilder.desc(root.get("time").as(String.class)));
                return query.getRestriction();
            }
        }, qPageRequest);
        return (!page.isEmpty()) ?
                NetMessage.successNetMessage("", page) :
                NetMessage.failNetMessage("", "暂无发现！！");
    }

    @Override
    public NetMessage saveComment(NewsComment newsComment) {

        String newsid = newsComment.getNewsid();
        Optional<News> byId = newsRepository.findById(Integer.parseInt(newsid));
        if (!byId.isPresent()) return NetMessage.failNetMessage("", "评论的新闻不存在！！");

        String userid = newsComment.getUserid();
        NetMessage info = accountService.getUser(userid);
        if (info.getStatus() == NetMessage.FAIl) return info;


        newsCommentRepository.save(newsComment);
        return NetMessage.successNetMessage("", "评论成功！！");
    }

    /**
     * 分页获取评论信息
     * 如果查询条件有新闻标题，根据新闻标题查询
     *
     * @param newsComment
     * @param start       起始记录数
     * @param end         结束记录数
     * @return
     */
    @Override
    public NetMessage getComment(NewsComment newsComment, final Integer start, final Integer end) {
        if (start == 0 || end == 0) return NetMessage.failNetMessage("", "需要查询记录区间！！");
        QPageRequest qPageRequest = new QPageRequest(start, end);
        Page<NewsComment> page = newsCommentRepository.findAll(new Specification<NewsComment>() {
            @Override
            public Predicate toPredicate(Root<NewsComment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String newsid = newsComment.getNewsid();
                if (!CharacterUtil.isEmpty(newsid)) {
                    list.add(criteriaBuilder.equal(root.get("newsid").as(String.class), newsid));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        }, qPageRequest);
        return (!page.isEmpty()) ?
                NetMessage.successNetMessage("", page) :
                NetMessage.failNetMessage("", "暂无评论！！");
    }

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsCommentRepository newsCommentRepository;
    @Autowired
    private AccountService accountService;

}
