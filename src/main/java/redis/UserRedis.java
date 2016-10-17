package redis;

public class UserRedis extends BaseRedis {
    private static final String ID_PREFIX = "user:";
    private static final String COLUMN_IP = "ip";

    public void add(String id, String ip) {
        getRedisTemplate().opsForHash().put(ID_PREFIX + id, COLUMN_IP, ip);
    }

    public void update(final String id, final String ip) {
        getRedisTemplate().opsForHash().put(ID_PREFIX + id, COLUMN_IP, ip);
    }

    public void delete(final String id) {
        getRedisTemplate().opsForHash().delete(ID_PREFIX + id);
    }

    public Boolean has(final String id) {
        return getRedisTemplate().opsForHash().hasKey(ID_PREFIX + id, COLUMN_IP);
    }

    public String get(final String id) {
        return (String) getRedisTemplate().opsForHash().get(ID_PREFIX + id, COLUMN_IP);
    }
}
