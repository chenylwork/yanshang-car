package com.yanshang.car.repositories;

import com.yanshang.car.bean.Code;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName CodeRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/31- 17:16
 * @Version 1.0
 **/
@Repository
public interface CodeRepository extends JpaRepositoryImplementation<Code,Integer> {
    /**
     * 根据类别获取字典集合
     * @param type
     * @return
     */
    List<Code> getByType(String type);
}
