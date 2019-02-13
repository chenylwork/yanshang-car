package com.yanshang.car.bean.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @ClassName AccountActivityView
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 9:52
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="v_account_activity")
@AllArgsConstructor
@NoArgsConstructor
public class AccountActivityView {
    @Id
    private Integer dataid;
    private String activityid;
    private String accountid;
    private String title;
    private String content;
    private String start;
    private String end;
    private String image;

}
