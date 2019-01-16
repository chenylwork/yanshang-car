package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * @ClassName LoanOrder
 * @Description 贷款订单表
 * @Author 陈彦磊
 * @Date 2019/1/16- 15:33
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_order_loan")
@AllArgsConstructor
@NoArgsConstructor
public class LoanOrder{

}
