package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * @ClassName Notice
 * @Description 系统公告
 * @Author 陈彦磊
 * @Date 2019/2/13- 16:27
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notice")
public class Notice {
    @Id
    private String noticeid; // 主键编号
    private String notice; // 公告内容
    private String version; // 公告记录（第几条通知）

}

