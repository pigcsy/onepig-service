package com.one.pig.token;

import com.one.pig.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {



    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), "".toCharArray(), getName());
        return info;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        LoginUser loginUser = (LoginUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(permissionService.getRoles(loginUser.getId(), SystemCfg.app_id));
//        authorizationInfo.setStringPermissions(permissionService.getPermissions(loginUser.getId(), SystemCfg.app_id));

        return authorizationInfo;

//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
//        Integer role = shiroUser.getScope();
//
//        Set<String> permissionSet = new HashSet<>();
//        Set<String> roleNameSet = new HashSet<>();
//        roleNameSet.add(role.toString());
//        permissionSet.add(shiroUser.getScope().toString());
//        //后台管理员,使用方式
//        Admin admin = (Admin) userService.getAttribute("KEY_CURRENT_USER_ID");
//        if (admin != null) {
//            Set<String> roles = new HashSet();
//            if (admin.getRole() != null) {
//                roles.addAll(Arrays.asList(admin.getRole()));
//            }
//            info.setRoles(roles);
//        }
//        info.addStringPermissions(permissionSet);
//        info.addRoles(roleNameSet);
//        return info;
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
       SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
       super.clearCachedAuthorizationInfo(principals);
   }

   /**
    * 指定principalCollection 清除
    */
   @Override
   public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
       SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
       super.clearCachedAuthorizationInfo(principals);
   }
}
