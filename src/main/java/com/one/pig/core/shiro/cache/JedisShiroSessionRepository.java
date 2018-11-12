package com.one.pig.core.shiro.cache;

import com.anniu.ordercall.core.redis.RedisCacheManager;
import com.anniu.ordercall.core.shiro.session.CustomSessionManager;
import com.anniu.ordercall.core.shiro.session.SessionStatus;
import com.anniu.ordercall.core.shiro.session.ShiroSessionRepository;
import com.anniu.ordercall.core.utils.LoggerUtils;
import com.anniu.ordercall.core.utils.SerializeUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Session 管理
 *
 * @author anniu.com
 */
@SuppressWarnings("unchecked")
@Component
public class JedisShiroSessionRepository implements ShiroSessionRepository {
    public static final String REDIS_SHIRO_SESSION = "ordercall-shiro-cache-session:";
    //这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
    public static final String REDIS_SHIRO_ALL = "*ordercall-shiro-cache-session:*";
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 1;
    protected Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = SerializeUtils.serialize(buildRedisSessionKey(session.getId()));
            //不存在才添加。
            if (null == session.getAttribute(CustomSessionManager.SESSION_STATUS)) {
                //Session 踢出自存存储。
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }

            byte[] value = SerializeUtils.serialize(session);


            /**这里是我犯下的一个严重问题，但是也不会是致命，
             * 我计算了下，之前上面不小心给我加了0，也就是 18000 / 3600 = 5 个小时
             * 另外，session设置的是30分钟的话，并且加了一个(5 * 60)，一起算下来，session的失效时间是 5:35 的概念才会失效
             * 我原来是存储session的有效期会比session的有效期会长，而且最终session的有效期是在这里【SESSION_VAL_TIME_SPAN】设置的。
             *
             * 这里没有走【shiro-config.properties】配置文件，需要注意的是【aspring-shiro.xml】 也是直接配置的值，没有走【shiro-config.properties】
             *
             * PS  : 注意： 这里我们配置 redis的TTL单位是秒，而【aspring-shiro.xml】配置的是需要加3个0（毫秒）。
             long sessionTimeOut = session.getTimeout() / 1000;
             Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
             */

            redisCacheManager.put(String.valueOf(key), String.valueOf(value), (long) (session.getTimeout() / 1000));
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "save session error，userId:[%s]", session.getId());
        }
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session userId is empty");
        }
        try {
            redisCacheManager.del(String.valueOf(SerializeUtils.serialize(buildRedisSessionKey(id))));
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，userId:[%s]", id);
        }
    }


    @Override
    public Session getSession(Serializable id) {
        if (id == null)
            throw new NullPointerException("session userId is empty");
        Session session = null;
        try {
            byte[] value = redisCacheManager.get(String.valueOf(SerializeUtils.serialize(buildRedisSessionKey(id)))).getBytes();
            session = SerializeUtils.deserialize(value, Session.class);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取session异常，userId:[%s]", id);
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        Set<Session> sessions = new HashSet<Session>();
        List<String> byteKeys = redisCacheManager.getAllkey(REDIS_SHIRO_ALL);

        try {
            if (byteKeys != null && byteKeys.size() > 0) {
                for (String bs : byteKeys) {
                    Session obj = SerializeUtils.deserialize(redisCacheManager.get(bs).getBytes(), Session.class);
                    if (obj instanceof Session) {
                        sessions.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
        }
        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }
}
