package com.yanshang.car.repositories;

import com.yanshang.car.bean.Employee;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName EmployeeRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/28- 15:44
 * @Version 1.0
 **/
@Repository
public interface EmployeeRepository extends JpaRepositoryImplementation<Employee,Integer>{

}
