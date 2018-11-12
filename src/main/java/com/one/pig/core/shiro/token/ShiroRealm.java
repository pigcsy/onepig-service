package com.one.pig.core.shiro.token;


import com.alibaba.fastjson.JSONObject;
import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.core.shiro.service.SessionService;
import com.anniu.ordercall.core.utils.ToolUtils;
import com.anniu.ordercall.model.ordercall.User;
import com.anniu.ordercall.service.user.UserService;
import com.anniu.ordercall.service.user.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ShiroRealm extends AuthorizingRealm {
    Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RedisOperations redisOperations;
    @Autowired
    private SessionService sessionService;

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UserService shiroFactory = UserServiceImpl.me();
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = shiroFactory.user(token.getUsername());
        ShiroUser shiroUser = shiroFactory.shiroUser(user);
        SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, super.getName());
        String key = "sessionIdList:" + user.getId();
        String sessionId = ShiroUtil.getSession().getId().toString();
        redisOperations.opsForList().remove(key, 0, sessionId);
        redisOperations.opsForList().rightPush(key, sessionId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(shiroUser, userDto);
        sessionService.setAttribute("ORDER_CALL_USER_ID", userDto);
        logger.warn("login user:{} with sessionId:{}", JSONObject.toJSONString(user), sessionId);
        return info;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserService shiroFactory = UserServiceImpl.me();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        List<Integer> roleList = shiroUser.getRoleList();

        Set<String> permissionSet = new HashSet<>();
        Set<String> roleNameSet = new HashSet<>();

        for (Integer roleId : roleList) {
            List<String> permissions = shiroFactory.findPermissionsByroleId(roleId);
            if (permissions != null) {
                for (String permission : permissions) {
                    if (ToolUtils.isNotEmpty(permission)) {
                        permissionSet.add(permission);
                    }
                }
            }
            String roleName = shiroFactory.findRoleNameByroleId(roleId);
            roleNameSet.add(roleName);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionSet);
        info.addRoles(roleNameSet);
        return info;
    }

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }

    /**
     * 清空当前用户权限信息
     */
    public void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
