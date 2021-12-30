package com.example.projectdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Zero
 */
@Service
public class TestService {
    @Autowired
    private StringRedisTemplate redis;

    public String test(int id) {
        SessionCallback<Object> callback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String key = id + "_num";
                if (redis.hasKey(key) == null) {
                    redis.opsForValue().set(key, "100");
                }
                operations.watch(key);
                int num = Integer.valueOf(redis.opsForValue().get(key));
                if (num <= 0) {
                    return "fail";
                } else {
                    operations.multi();
                    operations.opsForValue().decrement(key);
                    return operations.exec();
                }
            }
        };
        if (redis.execute(callback).equals("fail")) {
            return "fail";
        }
        System.out.println(redis.execute(callback));
        return "true";
    }

}
