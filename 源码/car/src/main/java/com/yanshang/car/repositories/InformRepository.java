package com.yanshang.car.repositories;

import com.yanshang.car.bean.Inform;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName InformRepository
 * @Description 通知数据操作
 * @Author 陈彦磊
 * @Date 2019/2/13- 16:29
 * @Version 1.0
 **/
@Repository
public interface InformRepository extends MongoRepository<Inform,String>{
    /**
     * 获取未读通知信息
     * @param version
     * @return
     */
    List<Inform> getByVersionAfter(String version);
}
