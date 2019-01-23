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
            System.out.println(map);
        }
    }


}

