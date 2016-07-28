package database.kvs.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseRedis {
    private static RedisTemplate rTemplate = null;

    public BaseRedis() {
        if (rTemplate == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("/spring/redis.xml");
            rTemplate = (RedisTemplate) context.getBean("redisTemplate");
        }
    }

    private RedisTemplate getRedisTemplate() {
        if (rTemplate == null)
            return null;

        return rTemplate;
    }

    private String isRedisWork() {
        return (String) rTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    protected void putAll(final String key, Map<Object, Object> data) throws RedisDatabaseException {
        try {
            rTemplate.boundHashOps(key).putAll(data);
        } catch (Exception e) {
            String log = "putAll(key, data) exeption!\n";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }

    protected Boolean put(final String key, final String column, final byte[] data) throws RedisDatabaseException {
        try {
            return (Boolean) rTemplate.execute(new RedisCallback() {
                public Boolean doInRedis(RedisConnection connection) {
                    return !connection.hSet(key.getBytes(), column.getBytes(), data);   // 0 stands for success.
                }
            });
        } catch (Exception e) {
            String log = "update(key, column, data) exception!\n ";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }

    protected Boolean hSet(final String key, final String column, final byte[] data) throws RedisDatabaseException {
        try {
            return (Boolean) rTemplate.execute(new RedisCallback() {
                public Boolean doInRedis(RedisConnection connection) {
                    return !connection.hSet(key.getBytes(), column.getBytes(), data);   // 0 stands for success.
                }
            });
        } catch (Exception e) {
            String log = "update(key, column, data) exception!\n ";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }

    protected Boolean del(final String key) throws RedisDatabaseException {
        try {
            return (Boolean) rTemplate.execute(new RedisCallback() {
                public Boolean doInRedis(RedisConnection connection) {
                    return (connection.del(key.getBytes()) == 1);    //1 stands for success.
                }
            });
        } catch (Exception e) {
            String log = "delete(key) exeption!\n";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }

    protected byte[] hGet(final String key, final String column) throws RedisDatabaseException {
        try {
            return (byte[]) rTemplate.execute(new RedisCallback() {
                public Object doInRedis(RedisConnection connection) {
                    return connection.hGet(key.getBytes(), column.getBytes());
                }
            });
        } catch (Exception e) {
            String log = "hGet(key, column) exception!\n";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }

    protected Boolean exists(final String key) throws RedisDatabaseException {
        try {
            return (Boolean) rTemplate.execute(new RedisCallback() {
                public Boolean doInRedis(RedisConnection connection) {
                    return connection.exists(key.getBytes());
                }
            });
        } catch (Exception e) {
            String log = "exists(key) exception!\n";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }

    /* Find all keys matching the given pattern */
    protected Set<String> keys(final String pattern) throws RedisDatabaseException {
        RedisTemplate redisTemplate = getRedisTemplate();

        try {
            Set<byte[]> keySet = (Set<byte[]>) redisTemplate.execute(new RedisCallback() {
                public Set<byte[]> doInRedis(RedisConnection connection) {
                    return connection.keys(pattern.getBytes());
                }
            });
            Set<String> keys = new HashSet<>();
            for (byte[] bytes : keySet) {
                keys.add(new String(bytes));
            }
            return keys;
        } catch (Exception e) {
            String log = "keys(pattern) exception!\n ";
            RedisDatabaseException redisDatabaseException = new RedisDatabaseException(log + e.getMessage());
            throw redisDatabaseException;
        }
    }
}
