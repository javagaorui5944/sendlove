package com.gaorui.redisdao;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;



@Component
public class redisdao {

	@Resource
    private StringRedisTemplate redisTemplate;
	public boolean redis_addOrder(long school_id,String message) {

	
	
		String id = school_id+":";
		redisTemplate.opsForValue().set(id, message);
		redisTemplate.expire(id, 1, TimeUnit.HOURS);
		return true;
	}

	public boolean redis_deleteOrder(long school_id) {
		redisTemplate.delete("order:"+school_id);
		return true;
	}

	public String redis_getOrder(long school_id) {
		String id = school_id+":";
		String x = redisTemplate.opsForValue().get(id);
		return x;
	}
	
}
