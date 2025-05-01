package org.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testSet() {
        //向redis中存储一个键值对
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("name", "zhangsan");
    }

    @Test
    public void testGet() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String name = ops.get("username");
        System.out.println(name);
    }
}
