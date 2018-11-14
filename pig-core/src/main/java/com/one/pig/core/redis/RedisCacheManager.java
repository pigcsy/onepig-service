package com.one.pig.core.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.RedisSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheManager implements CacheManager {
    private static int DEFAULT_EXPIRE_TIME = 60;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisManager redisManager;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public RedisCacheManager() {
    }

    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate, IRedisManager redisManager) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(String key, Class<T> t) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    public String get(String key) {
        Object obj = redisTemplate.boundValueOps(key).get();
        return obj == null ? null : obj.toString();
    }

    public void put(String key, Object v) {
        redisTemplate.boundValueOps(key).set(v);
    }

    public void put(String key, Object v, long expire) {
        BoundValueOperations<String, Object> oper = redisTemplate.boundValueOps(key);
        oper.set(v);
        if (expire > 0)
            oper.expire(expire, TimeUnit.SECONDS);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    public <T> T getQ(String key, Class<T> t) {
        return (T) redisTemplate.boundListOps(key).leftPop();
    }

    /**
     * 查询缓存里从start到end的数据
     */
    public <T> List<T> getAllList(String key, long start, long end) {

        return (List<T>) redisTemplate.boundListOps(key).range(start, end);
    }

    /**
     * 查询缓存里从start到end的数据
     */
    public <T> List<T> getAllkey(String key) {
        return (List<T>) redisTemplate.keys(key);
    }

    /**
     * 一次获取number个
     */
    public <T> List<T> getListViaNum(String key, Class<T> t, long number) {
        List<T> list = new ArrayList<>();
        long num = redisTemplate.boundListOps(key).size();
        if (number <= 0)
            return list;
        if (num < number) {
            number = num;
        }
        for (int i = 0; i < number; i++) {
            list.add((T) redisTemplate.boundListOps(key).leftPop());
        }
        return list;
    }

    public void putQ(String key, Object obj) {
        redisTemplate.boundListOps(key).rightPush(obj);
    }

    public void lock(String key, long timeout) {
        try {
            long nano = System.nanoTime();
            do {
                Boolean opt = redisTemplate.boundValueOps(key).setIfAbsent(key);
                if (opt) {
                    redisTemplate.boundValueOps(key).expire(DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                    return;
                } else { // 存在锁
                    logger.warn("已被加锁");
                }
                if (timeout == 0)
                    break;
                Thread.sleep(100);
            } while ((System.nanoTime() - nano) < TimeUnit.SECONDS.toNanos(timeout));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unLock(String key) {
        this.del(key);
    }

    public void putHash(final String key, final String field, final Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    public void putHashInExpire(final String key, final String field, final Object value, final long expire) {
        BoundHashOperations oper = redisTemplate.boundHashOps(key);
        oper.put(field, value);
        if (expire > 0) {
            oper.expire(expire, TimeUnit.SECONDS);
        }
    }

    public <T> T getHash(final String key, final String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    public long incr(final String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 放到set集合中
     *
     * @param key
     * @param v
     * @param expire
     */
    public void putSet(String key, Object v, long expire) {
        BoundSetOperations<String, Object> oper = redisTemplate.boundSetOps(key);
        oper.add(v);
        if (expire > 0)
            oper.expire(expire, TimeUnit.SECONDS);
    }

    public <T> Set<T> getSet(String key, Class<T> t, long count) {
        long size = redisTemplate.boundSetOps(key).size();
        if (size >= count) {
            return (Set<T>) redisTemplate.boundSetOps(key).distinctRandomMembers(count);
        } else {
            return (Set<T>) redisTemplate.boundSetOps(key).distinctRandomMembers(size);
        }
    }

    /**
     * 判断obj是否在set集合中
     *
     * @param key
     * @param obj 待判断对象
     * @return true 存在，false不存在
     */
    public boolean isInSet(String key, Object obj) {
        return redisTemplate.boundSetOps(key).isMember(obj);
    }


    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();


    private RedisSerializer keySerializer = new StringSerializer();
    private RedisSerializer valueSerializer = new ObjectSerializer();

    // expire time in seconds
    public static final int DEFAULT_EXPIRE = 1800;
    private int expire = DEFAULT_EXPIRE;

    /**
     * The Redis key prefix for caches
     */
    public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";
    private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;

    public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "id";
    private String principalIdFieldName = DEFAULT_PRINCIPAL_ID_FIELD_NAME;

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");

        Cache c = caches.get(name);

        if (c == null) {
            RedisManager redisManager=new RedisManager();

            // initialize the Redis manager instance

            // create a new cache instance
            c = new RedisCache<K, V>(redisManager, keySerializer, valueSerializer, keyPrefix + name + ":", expire, principalIdFieldName);
            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}
