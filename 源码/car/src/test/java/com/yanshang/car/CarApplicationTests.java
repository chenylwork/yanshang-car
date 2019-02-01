package com.yanshang.car;

import com.yanshang.car.bean.Car;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.commons.ObjectUtils;
import com.yanshang.car.dao.MongodbDao;
import com.yanshang.car.repositories.CarRepository;
import com.yanshang.car.services.CarService;
import com.yanshang.car.sms.SMSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarApplicationTests {

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private MongodbDao mongodbDao;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {
        String zhangsan = redisTemplate.opsForValue().get("zhangsan");
        System.out.println(zhangsan);
    }

	/**
	 * 为sms加入白名单
	 */
	@Test
	public void sms() {
        NetMessage trust = SMSUtil.trust();
        System.out.println(trust);
    }
    @Test
    public void mongo() {
        Car car = carRepository.findById(1).get();
        HashMap<String, Object> carMap = ObjectUtils.object2Map(car);
        mongodbDao.save(carMap,"car");
    }
    @Test
    public void mongoGet() {
        List<HashMap> carList = mongodbDao.get("car");
        for (HashMap map : carList) {
//            System.out.println(map);
        }
    }

    @Test
    public void mongoTest() {
        Criteria where = Criteria.where("basic.carid").is("6");
        Query query = new Query(where);
        List<Object> car = mongoTemplate.find(query, Object.class, "car");
        System.out.println("输出的结果开始");
        System.out.println(car);
        System.out.println("输出的结果结束");
    }


}

