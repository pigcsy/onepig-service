package com.one.pig.session;

import com.one.pig.core.redis.RedisCacheManager;
import com.one.pig.core.util.base.SpringUtil;
import com.one.pig.core.util.common.SerializeUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SuppressWarnings({"rawtypes", "unchecked"})
@Component("redisSessionDAO")
@Lazy(false)
public class RedisSessionDAO extends AbstractSessionDAO {

    /**
     * The Redis key prefix for the sessions
     */


    public static final String REDIS_SHIRO_SESSION = "credit-shiro-session:";
    public static final String REDIS_SHIRO_ALL = "*credit-shiro-cache-session:*";
    /**
     * session status
     */
    public static final String SESSION_STATUS = "credit-online-status";
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    public void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            throw new NullPointerException("session is empty");
        }
        try {
            String key = buildRedisSessionKey(session.getId());
            //不存在才添加。
            if (null == session.getAttribute(SESSION_STATUS)) {
                //Session 踢出自存存储。
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(SESSION_STATUS, sessionStatus);
            }

            byte[] value = SerializeUtils.serialize(session);

            SpringUtil.getBean(RedisCacheManager.class, "redisCacheManager").put(key, Base64.getEncoder().encodeToString(value), (long) (session.getTimeout()));
        } catch (Exception e) {
            logger.info("save session error，id:[%s]", session.getId());
        }
    }


    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        SpringUtil.getBean(RedisCacheManager.class, "redisCacheManager").del(this.getByteKey(session.getId()));

    }

    // @Override
    // public void deleteSession(Serializable id) {
    // 	if (id == null) {
    // 		throw new NullPointerException("session id is empty");
    // 	}
    // 	try {
    // 		redisCacheManager.del(String.valueOf(SerializeUtils.serialize(buildRedisSessionKey(id))));
    // 	} catch (Exception e) {
    // 		logger.info( "删除session出现异常，id:[%s]", id);
    // 	}
    // }


    //用来统计当前活动的session
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        List<String> byteKeys = SpringUtil.getBean(RedisCacheManager.class, "redisCacheManager").getAllkey(REDIS_SHIRO_ALL);

        try {
            if (byteKeys != null && byteKeys.size() > 0) {
                for (String bs : byteKeys) {
                    Session obj = SerializeUtils.deserialize(SpringUtil.getBean(RedisCacheManager.class, "redisCacheManager").get(bs).getBytes(), Session.class);
                    if (obj instanceof Session) {
                        sessions.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            logger.info("获取全部session异常");
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }
        if (SpringUtil.getBean(RedisCacheManager.class, "redisCacheManager").get(getByteKey(sessionId)) != null) {
            Session s = (Session) SerializeUtils.deserialize(Base64.getDecoder().decode(SpringUtil.getBean(RedisCacheManager.class, "redisCacheManager").get(getByteKey(sessionId))));
            return s;
        } else {
            return null;
        }
    }

    /**
     * 获得byte[]型的key
     *
     * @param
     * @return
     */
    private String getByteKey(Serializable sessionId) {
        String preKey = REDIS_SHIRO_SESSION + sessionId;
        return preKey;
    }


    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    //
    // private UserOnlineDto getSessionBo(Session session) {
    //     //获取session登录信息。
    //     Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
    //     if (null == obj) {
    //         return null;
    //     }
    //     //确保是 SimplePrincipalCollection对象。
    //     if (obj instanceof SimplePrincipalCollection) {
    //         SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
    //         /**
    //          * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
    //          * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
    //          */
    //         obj = spc.getPrimaryPrincipal();
    //         if (null != obj && obj instanceof User) {
    //             //存储session + user 综合信息
    //             UserOnlineDto userOnlineDto = new UserOnlineDto();
    //             //最后一次和系统交互的时间
    //             userOnlineDto.setLastAccess(session.getLastAccessTime());
    //             //主机的ip地址
    //             userOnlineDto.setHost(session.getHost());
    //             //session ID
    //             userOnlineDto.setSessionId(session.getId().toString());
    //             //回话到期 ttl(ms)
    //             userOnlineDto.setTimeout(session.getTimeout());
    //             //session创建时间
    //             userOnlineDto.setStartTime(session.getStartTimestamp());
    //             //是否踢出
    //             SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
    //             boolean status = Boolean.TRUE;
    //             if (null != sessionStatus) {
    //                 status = sessionStatus.getOnlineStatus();
    //             }
    //             userOnlineDto.setSessionStatus(status);
    //             return userOnlineDto;
    //         }
    //     }
    //     return null;
    // }
}
