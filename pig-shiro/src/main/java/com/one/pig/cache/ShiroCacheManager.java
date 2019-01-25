package com.one.pig.cache;//package com.anniu.credit.user.shiro.cache;
//
//
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheException;
//import org.apache.shiro.cache.CacheManager;
//import org.springframework.data.redis.core.RedisOperations;
//
///**
// * 开发公司：anniu在线工具 <p>
// * 版权所有：© www.anniu.com<p>
// * 博客地址：http://www.anniu.com/blog/  <p>
// * <p>
// * <p>
// * shiro cache manager 接口
// * <p>
// * <p>
// * <p>
// * 区分　责任人　日期　　　　说明<br/>
// * 创建　csy　2016年6月2日 　<br/>
// *
// * @author csy
// * @version 1.0, 2016年6月2日 <br/>
// * @email so@anniu.com
// */
//public class ShiroCacheManager implements CacheManager {
//    public static final int TIME_OUT_SECOND = 86400 * 30;
//    private RedisOperations redisOperations;
//
//    public void setRedisOperations(RedisOperations redisOperations) {
//        this.redisOperations = redisOperations;
//    }
//
//    @Override
//    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
//        return new ShiroCache(name, TIME_OUT_SECOND, redisOperations);
//    }
//}
