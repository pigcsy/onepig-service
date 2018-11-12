package com.one.pig.core.shiro.cache;

import com.anniu.ordercall.core.redis.RedisCacheManager;
import com.anniu.ordercall.core.utils.LoggerUtils;
import com.anniu.ordercall.core.utils.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 开发公司：anniu.com<br/>
 * 版权：anniu.com<br/>
 * <p>
 * <p>
 * 缓存获取Manager
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　csy　2016年4月29日 　<br/>
 * <p>
 * *******
 * <p>
 *
 * @author csy
 * @version 1.0, 2016年4月29日 <br/>
 * @email json@anniu.com
 */
@SuppressWarnings("unchecked")
@Component
public class JedisShiroCache<K, V> implements Cache<K, V> {
    static final Class<JedisShiroCache> SELF = JedisShiroCache.class;
    /**
     * 为了不和其他的缓存混淆，采用追加前缀方式以作区分
     */
    private static final String REDIS_SHIRO_CACHE = "ordercall-shiro-cache:";
    /**
     * Redis 分片(分区)，也可以在配置文件中配置
     */
    private static final int DB_INDEX = 1;
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisCacheManager redisCacheManager;
    private String name;

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public V get(K key) throws CacheException {
        byte[] byteKey = SerializeUtils.serialize(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = redisCacheManager.get(String.valueOf(byteKey)).getBytes();
        } catch (Exception e) {
            LoggerUtils.error(SELF, "get value by cache throw exception", e);
        }
        return (V) SerializeUtils.deserialize(byteValue);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            redisCacheManager.put(String.valueOf(SerializeUtils.serialize(buildCacheKey(key))), SerializeUtils.serialize(value), -1);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "put cache throw exception", e);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            redisCacheManager.del(String.valueOf(SerializeUtils.serialize(buildCacheKey(key))));
        } catch (Exception e) {
            LoggerUtils.error(SELF, "remove cache  throw exception", e);
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        //TODO--
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        //TODO
        return null;
    }

    @Override
    public Collection<V> values() {
        //TODO
        return null;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }

}
