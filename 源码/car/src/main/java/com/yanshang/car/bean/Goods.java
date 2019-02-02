package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Commodity
 * @Description 商品信息
 * @Author 陈彦磊
 * @Date 2019/1/16- 16:52
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_commodity")
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commodityid; // 商品编号
    private String name; // 商品名称
    private String price; // 商品价格
    private String credits; // 商品对应积分
    private String count; // 商品库存
    private String information; // 商品详细信息

}
