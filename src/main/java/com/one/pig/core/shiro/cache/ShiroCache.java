package com.one.pig.core.shiro.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ShiroCache implements Cache {
    protected Logger log = LoggerFactory.getLogger(getClass());

    private RedisOperations redisOperations;
    private String name;
    private int timeout = 3600;

    public ShiroCache(String name, int timeout, RedisOperations redisOperations) {
        this.name = name;
        this.redisOperations = redisOperations;
        this.timeout = timeout;
    }

    @Override
    public Object get(Object key) throws CacheException {
        return redisOperations.opsForValue().get(name + ":" + key);
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        redisOperations.opsForValue().set(name + ":" + key, value, timeout, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        redisOperations.delete(name + ":" + key);
        return key;
    }

    @Override
    public void clear() throws CacheException {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
