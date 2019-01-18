package com.yanshang.car;

import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.sms.SMSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarApplicationTests {

	@Autowired
	private StringRedisTemplate redisTemplate;

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


}

