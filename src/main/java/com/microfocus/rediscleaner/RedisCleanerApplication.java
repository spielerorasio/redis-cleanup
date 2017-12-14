package com.microfocus.rediscleaner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringBootApplication
public class RedisCleanerApplication {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private String port;

	@PostConstruct
	public void cleanRedisKeys(){
		int counter = 0;
		System.out.println("About to clean redis keys with delimiter ':'. ");
		System.out.println("Redis address " + redisHost+ ":"+port);

		Set<String> keys = stringRedisTemplate.keys("*");
		for (String key : keys) {
			if(key.contains(":")) continue;
			counter++;
			stringRedisTemplate.delete(key);
		}

		System.out.println("Deleted "+counter+" keys from redis");
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisCleanerApplication.class, args).close();
	}
}
