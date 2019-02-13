package com.yanshang.car.bean.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @ClassName RecommendView
 * @Description 推荐人视图
 * @Author 陈彦磊
 * @Date 2019/2/13- 14:55
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="v_recommend")
@AllArgsConstructor
@NoArgsConstructor
public class RecommendView {
    @Id
    private Integer dataid; // 数据编号
    private String fromuser; // 被关注者编号
    private String fromhead; // 被关注者头像
    private String fromname; // 被关注者名称
    private String touser; // 关注着编号
    private String tohead; // 关注者头像
    private String toname; // 关注者名称

}
