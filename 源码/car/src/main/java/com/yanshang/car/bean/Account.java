package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Account
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/4- 16:30
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_account",uniqueConstraints = {@UniqueConstraint(columnNames = "phone")})
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone; // 手机号
    private String password; // 密码
    private String referrerName; // 推荐人姓名
    private String referrerPhone; // 推荐人电话

}
