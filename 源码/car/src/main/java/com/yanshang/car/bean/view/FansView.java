package com.yanshang.car.bean.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @ClassName FansView
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 14:05
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="v_fans")
@AllArgsConstructor
@NoArgsConstructor
public class FansView {

    @Id
    private Integer fansid; // 编号
    private String fromuser; // 被关注者编号
    private String fromhead; // 被关注者头像
    private String fromname; // 被关注者名称

    private String touser; // 注者编号
    private String tohead; // 注者编号
    private String toname; // 注者编号
}
