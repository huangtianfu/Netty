package redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseRedis {
    private static RedisTemplate redisTemplate = null;

    public BaseRedis() {
        if (redisTemplate == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("/spring/redis.xml");
            redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        }
    }

    protected RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}
