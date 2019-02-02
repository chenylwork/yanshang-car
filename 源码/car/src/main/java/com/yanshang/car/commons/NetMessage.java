package com.yanshang.car.commons;

import lombok.Data;

/*
 * @ClassName NetMessage
 * @Description 该实体主要用于前后端数据交互使用，
 * 前后台使用该对象的json结构进行数据交互
 * @Author 陈彦磊
 * @Date 2019/1/14- 11:11
 * @Version 1.0
 **/
@Data
public class NetMessage<T> {
    private String code; // 消息编号
    private T content; // 消息内容
    private int status; // 消息状态
    public static final int SUCCESS = 1;
    public static final int FAIl= 0;
    private NetMessage() {}

    private NetMessage(String code, T content) {
        this.code = code;
        this.content = content;
    }

    private NetMessage(String code, T content, int status) {
        this.code = code;
        this.content = content;
        this.status = status;
    }

    public NetMessage setContent(T content) {
        this.content = content;
        return this;
    }

    public static <T> NetMessage successNetMessage(String code, T content) {
        return new NetMessage(code,content,SUCCESS);
    }

    public static <T> NetMessage failNetMessage(String code, T content) {
        return new NetMessage(code,content,FAIl);
    }

    public static <T> NetMessage errorNetMessage() {
        return new NetMessage("50000","服务器错误，已经联系管理员，请稍后再试！！",FAIl);
    }

}
