package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * @ClassName Inform
 * @Description 通知
 * @Author 陈彦磊
 * @Date 2019/2/13- 16:24
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "inform")
public class Inform {

    @Id
    private String informid; // 主键编号
    private String inform; // 通知内容
    private String version; // 通知记录（第几条通知）

}
