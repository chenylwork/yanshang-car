package com.yanshang.car.services.impl;

import com.yanshang.car.bean.Account;
import com.yanshang.car.bean.Car;
import com.yanshang.car.bean.CarBrand;
import com.yanshang.car.bean.CarComment;
import com.yanshang.car.commons.CharacterUtil;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.repositories.AccountRepository;
import com.yanshang.car.repositories.CarBrandRepository;
import com.yanshang.car.repositories.CarCommentRepository;
import com.yanshang.car.repositories.CarRepository;
import com.yanshang.car.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * @ClassName CarServiceImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/17- 9:57
 * @Version 1.0
 **/
@Service("carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarBrandRepository carBrandRepository;
    @Autowired
    private CarCommentRepository carCommentRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public NetMessage getAllCarBrand() {
        List<CarBrand> all = carBrandRepository.findAll();
        if (all != null && !all.isEmpty()) return NetMessage.successNetMessage("",all);
        return NetMessage.failNetMessage("","暂无品牌！！");
    }

    @Override
    public NetMessage getRecommend(String brand) {
        List<Car> all = carRepository.findAll(new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 品牌拼接
                if (CharacterUtil.isEmpty(brand)) {
                    predicates.add(criteriaBuilder.equal(root.get("brand").as(String.class), brand));
                }
                // 标签拼接
                CriteriaBuilder.In<Object> labels = criteriaBuilder.in(root.get("label"));
                labels.value(CarRepository.LABEL_RECOMMEND);
                predicates.add(labels);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        if (all != null && !all.isEmpty()) return NetMessage.successNetMessage("",all);
        return NetMessage.failNetMessage("","暂无主打车！！");
    }

    @Override
    public NetMessage getDetails(String identity) {
        if (CharacterUtil.isEmpty(identity)) identity = "0";
        Optional<Car> byId = carRepository.findById(Integer.parseInt(identity));
        if(byId != null && byId.isPresent()) {
            return NetMessage.successNetMessage("",byId.get());
        }
        return NetMessage.failNetMessage("","没有您需要的汽车信息！！");
    }

    @Override
    public NetMessage getComments(String identity) {
        if (CharacterUtil.isEmpty(identity)) return NetMessage.failNetMessage("","需要汽车标识参数！！");
        List<CarComment> comments = carCommentRepository.getByObject(identity);
        if (comments != null && !comments.isEmpty()) {
            return NetMessage.successNetMessage("",comments);
        }
        return NetMessage.failNetMessage("","暂无评论！！");
    }

    @Override
    public NetMessage publishComments(CarComment comment) {
        String failContent = "";
        if (comment == null) return NetMessage.failNetMessage("", "对象为空！！");
        if (CharacterUtil.isEmpty(comment.getTitle())) return NetMessage.failNetMessage("","缺少标题！！");
        if (CharacterUtil.isEmpty(comment.getContent()))return NetMessage.failNetMessage("","缺少内容！！");

        String carID = comment.getObject();
        if (CharacterUtil.isEmpty(comment.getObject())) return NetMessage.failNetMessage("","缺少评论对象！！");
        Optional<Car> carOptional = carRepository.findById(Integer.parseInt(carID));
        if (carOptional == null || !carOptional.isPresent()) return NetMessage.failNetMessage("","评论对象不存在！！");

        String accountID = comment.getObserver();
        if (CharacterUtil.isEmpty(accountID)) return NetMessage.failNetMessage("","缺少评论人！！");
        Optional<Account> accountOptional = accountRepository.findById(Integer.parseInt(accountID));
        if (accountOptional == null || !accountOptional.isPresent())return NetMessage.failNetMessage("","评论人不存在！！");


        comment.setTime(CharacterUtil.dataTime());
        carCommentRepository.save(comment);
        return NetMessage.successNetMessage("","发表成功！！");
    }
}
