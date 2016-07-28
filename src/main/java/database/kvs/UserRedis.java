package database.kvs;

import database.kvs.base.BaseRedis;
import database.kvs.base.RedisDatabaseException;

import java.util.*;

public class UserRedis extends BaseRedis {
    private static final String ID_PREFIX = "user:";
    private static final String COLUMN_IP = "ip";

    public void add(String id, String ip) throws RedisDatabaseException {
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put(COLUMN_IP, ip);
        putAll(ID_PREFIX + id, data);
    }

    public void delete(final String id) throws RedisDatabaseException {
        del(ID_PREFIX + id);
    }

    public Boolean has(final String id) throws RedisDatabaseException {
        return exists(ID_PREFIX + id);
    }

    public String get(final String id) throws RedisDatabaseException {
        byte[] ip = hGet(ID_PREFIX + id, COLUMN_IP);
        if (ip != null) {
            return new String(ip);
        }
        return null;
    }

    public void update(final String id, final String ip) throws RedisDatabaseException {
        hSet(ID_PREFIX + id, COLUMN_IP, ip.getBytes());
    }
}
