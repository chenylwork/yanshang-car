package com.yanshang.car.repositories;

import com.yanshang.car.bean.Inform;
import com.yanshang.car.bean.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName NoticeRepository
 * @Description 系统公告数据操作
 * @Author 陈彦磊
 * @Date 2019/2/13- 16:28
 * @Version 1.0
 **/
@Repository
public interface NoticeRepository
        extends MongoRepository<Notice,String> {

    List<Notice> getByVersionAfter(String version);
}
