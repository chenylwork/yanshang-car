package com.yanshang.car;

import com.yanshang.car.commons.BaseDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CarApplication extends SpringBootServletInitializer {

//	private final static String password = "root";
	private final static String password = "3xju5kgn";
	private final static String username = "root";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		boolean database = BaseDao.createDatabase("car", "localhost", username, password);
		if (!database) return null;
		return builder.sources(CarApplication.class);
	}
	public static void main(String[] args) {
		boolean database = BaseDao.createDatabase("car", "localhost", username, password);
		if (!database) return;
		SpringApplication.run(CarApplication.class, args);
	}

}

