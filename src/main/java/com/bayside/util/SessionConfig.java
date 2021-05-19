package com.bayside.util;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@PropertySource("classpath:server.properties")
public class SessionConfig {
	@Resource
	private Environment environment;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		System.out.println("#####sessionredisip" + environment.getProperty("sessionredisip"));
		JedisConnectionFactory connection = new JedisConnectionFactory();
		connection.setPort(Integer.parseInt(environment.getProperty("redisport")));
		connection.setTimeout(15000);
		connection.setPassword(environment.getProperty("redispassword"));
		// connection.setHostName("27.54.216.197");
		/* connection.setHostName("192.168.1.88"); */
		connection.setHostName(environment.getProperty("sessionredisip"));
		return connection;
	}
}