package com.woniuxy.test;

import com.woniuxy.user.UserStarter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zh_o
 * @date 2020/10/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserStarter.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate srt;

    @Autowired
    private RedisTemplate<Object, Object> rt;

    @Autowired
    private RedisTemplate<String, Object> jrt;


    @Test
    public void test() {
        jrt.opsForHash().put("user:5","name", "2333");
        System.out.println(jrt.opsForHash().entries("user:5"));
    }

}
