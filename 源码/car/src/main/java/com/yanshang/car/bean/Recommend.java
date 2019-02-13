package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Recommend
 * @Description 推荐人关系表
 * @Author 陈彦磊
 * @Date 2019/2/13- 14:53
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_recommend")
@AllArgsConstructor
@NoArgsConstructor
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataid; // 推荐编号
    private String touser; // 推荐人编号
    private String fromuser; // 被推荐人编号
    @Transient
    private String code; // 被推荐人编号

}
