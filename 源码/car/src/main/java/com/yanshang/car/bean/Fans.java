package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Fans
 * @Description 粉丝
 * @Author 陈彦磊
 * @Date 2019/2/12- 14:31
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_fans")
@AllArgsConstructor
@NoArgsConstructor
public class Fans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fansid; // 编号
    private String touser; // 关注者
    private String fromuser; // 被关注者
}
