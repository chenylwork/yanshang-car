package com.yanshang.car.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * @ClassName MongodbDao
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/23- 10:06
 * @Version 1.0
 **/
@Component
public class MongodbDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param object
     * @param collectionName
     */
    public <T> T save(T object,String collectionName) {
        return mongoTemplate.save(object, collectionName);
    }
    public List<HashMap> get(String collectionName) {
        return mongoTemplate.findAll(HashMap.class, collectionName);
    }
}
