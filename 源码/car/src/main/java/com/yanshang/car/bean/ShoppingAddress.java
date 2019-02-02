package com.yanshang.car.bean;

import com.yanshang.car.commons.Dictionaries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Direction
 * @Description 收件人地址
 * @Author 陈彦磊
 * @Date 2019/1/16- 17:13
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_shop_address")
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer directionid; // 编号
    private String accountid; // 地址所属用户编号

    private String name; // 收件人
    private String phone; // 收件人电话
    private String region; // 收件人地区
    private String address; // 收件人详细地址
    /**
     * 是你否是默认地址：
     * {@link Dictionaries#UN_DEFAULT_DIRECTION} 不是默认地址，
     * {@link Dictionaries#DEFAULT_DIRECTION}是默认地址
     */
    private String def = Dictionaries.UN_DEFAULT_DIRECTION;

}
