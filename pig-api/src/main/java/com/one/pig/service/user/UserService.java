package com.one.pig.service.user;

import com.one.pig.ShiroUser;
import com.one.pig.core.bean.UserDto;
import com.one.pig.system.model.User;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

public interface UserService {
    /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    User user(String account);

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    ShiroUser shiroUser(User user);

    /**
     * 获取shiro的认证信息
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName);

    /**
     * 发送短信前判断账户是否存在
     *
     * @param account
     */
    void checkAccount(String account);


    /**
     * 修改用户密码
     *
     * @param account
     * @param newPwd
     */
    void changeUserPwd(String account, String newPwd);

    /**
     * 新增用户
     *
     * @param user
     */
    void insert(UserDto user);

    /**
     * 修改用户状态（客服）
     *
     * @param userId
     * @param status
     */
    void editUserStatus(Integer userId, String status);



    void editUser(Integer userId, String name, String password);
}
