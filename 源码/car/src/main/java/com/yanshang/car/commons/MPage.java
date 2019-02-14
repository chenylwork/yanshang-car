package com.yanshang.car.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @ClassName Page
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/1- 17:08
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MPage<T> {
    private int no; // 页码
    private int length; // 每页长度
    private long count; // 总数
    private List<T>  data; // 数据集合

    public MPage(int no, int length) {
        this.no = no;
        this.length = length;
    }

}
