package com.yanshang.car.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * @ClassName Code
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/31- 17:13
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_code")
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codeid; // 字典id
    private String code; // 字典编号
    private String name; // 字典名称
    private String type; // 字典类别
}
