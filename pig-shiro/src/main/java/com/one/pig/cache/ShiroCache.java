package com.one.pig.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ShiroCache implements Cache {

	protected Logger log = LoggerFactory.getLogger(getClass());

	private RedisTemplate redisTemplate;
	private String        name;
	private int           timeout = 3600;

	public ShiroCache(String name, int timeout, RedisTemplate redisTemplate) {
		this.name = name;
		this.redisTemplate = redisTemplate;
		this.timeout = timeout;
	}

	@Override
	public Object get(Object key) throws CacheException {
		return redisTemplate.opsForValue().get(name + ":" + key);
	}

	@Override
	public Object put(Object key, Object value) throws CacheException {
		redisTemplate.opsForValue().set(name + ":" + key, value, timeout, TimeUnit.SECONDS);
		return value;
	}

	@Override
	public Object remove(Object key) throws CacheException {
		redisTemplate.delete(name + ":" + key);
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
