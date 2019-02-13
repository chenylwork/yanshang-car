package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName AccountActivity
 * @Description 用户活动关系表映射
 * @Author 陈彦磊
 * @Date 2019/2/13- 9:48
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_account_activity")
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dataid; // 编号
    private String accountid; // 用户编号
    private String activityid; // 活动编号

}
