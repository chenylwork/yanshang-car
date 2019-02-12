package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * @ClassName Idea
 * @Description 意见反馈
 * @Author 陈彦磊
 * @Date 2019/2/12- 17:24
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "idea")
public class Idea {
    private String ideaid; // 意见编号
    private String phone; // 意见电话
    private String qq; // 留言者qq
    private String content; // 留言者内容
}
