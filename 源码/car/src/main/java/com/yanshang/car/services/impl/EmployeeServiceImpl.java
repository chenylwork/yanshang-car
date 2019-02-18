package com.yanshang.car.services.impl;

import com.yanshang.car.bean.Employee;
import com.yanshang.car.bean.ReserveOrder;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.MPage;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.repositories.EmployeeRepository;
import com.yanshang.car.repositories.ReserveOrderRepository;
import com.yanshang.car.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/*
 * @ClassName EmployeeServiceImpl
 * @Description 员工服务类
 * @Author 陈彦磊
 * @Date 2019/1/28- 15:42
 * @Version 1.0
 **/
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public NetMessage saveSalesmen(Employee employee) {
        employeeRepository.save(employee);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getSalesmen(Employee employee) {
        List<Employee> all = employeeRepository.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                String name = employee.getName();
                if (name != null && !"".equals(name)) {
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class),name));
                }
                Integer employeeid = employee.getEmployeeid();
                if (employeeid != null && employeeid != 0) {
                    list.add(criteriaBuilder.equal(root.get("employeeid").as(String.class),employeeid));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        });
        return (all == null || all.isEmpty()) ?
                NetMessage.failNetMessage("","没有您需要的信息！！"):
                NetMessage.successNetMessage("",all);
    }

    @Override
    public NetMessage saveReserve(ReserveOrder reserve) {
        if (reserve.getReserveid() == null) {
            reserve.setCreateTime(CharacterUtil.dataTime());
        }
        reserveOrderRepository.save(reserve);
        return NetMessage.successNetMessage("","保存成功！！");
    }

    @Override
    public NetMessage getReserve(ReserveOrder reserve, MPage<ReserveOrder> page) {
        if (!CharacterUtil.isEmpty(reserve.getAccountid())){
            List<ReserveOrder> orderList = reserveOrderRepository.getByAccountid(reserve.getAccountid());
            if(orderList != null && !orderList.isEmpty())
                return NetMessage.successNetMessage("",orderList);
        }
        int length = page.getLength();
        length = (length == 0) ? 10 : length;
        int start = page.getNo()*length;
        QPageRequest qPageRequest = new QPageRequest(start,length);
        Page<ReserveOrder> all = reserveOrderRepository.findAll(qPageRequest);

        if (all != null && !all.isEmpty()){
            page.setCount(all.getTotalElements());
            page.setData(all.getContent());
            return NetMessage.successNetMessage("",page);
        }
        return NetMessage.failNetMessage("","没有您需要的信息！！");
    }

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ReserveOrderRepository reserveOrderRepository;
}
