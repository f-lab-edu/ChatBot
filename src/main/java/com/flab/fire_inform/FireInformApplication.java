package com.flab.fire_inform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
public class FireInformApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireInformApplication.class, args);
	}

}
