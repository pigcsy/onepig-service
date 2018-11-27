package com.one.pig.service.user.impl;


import com.one.pig.ShiroUser;
import com.one.pig.ShiroUtil;
import com.one.pig.common.constant.factory.ConstantFactory;
import com.one.pig.core.bean.UserDto;
import com.one.pig.core.enums.UserStatusEnum;
import com.one.pig.core.exception.ApiException;
import com.one.pig.core.redis.RedisCacheManager;
import com.one.pig.core.util.common.BeanUtils;
import com.one.pig.core.util.common.ConvertUtils;
import com.one.pig.core.util.common.SpringContextHolder;
import com.one.pig.service.user.UserService;
import com.one.pig.system.dao.MenuMapper;
import com.one.pig.system.dao.UserMapper;
import com.one.pig.system.model.User;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.one.pig.common.exception.BizExceptionEnum.ACCOUNT_FREEZED;
import static com.one.pig.common.exception.BizExceptionEnum.HAD_THIS_USER;
import static com.one.pig.common.exception.BizExceptionEnum.NO_THIS_USER;
import static com.one.pig.common.exception.BizExceptionEnum.OLD_PWD_NOT_RIGHT;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;


    public static UserService me() {
        return SpringContextHolder.getBean(UserService.class);
    }

    @Override
    public User user(String account) {
        User user = userMapper.getByAccount(account);
        // 账号不存在
        if (null == user) {
            throw new ApiException(NO_THIS_USER.getCode(), NO_THIS_USER.getMessage());
        }
        //账号被冻结
        if (user.getStatus() != UserStatusEnum.OK.getCode()) {
            throw new ApiException(ACCOUNT_FREEZED.getCode(), ACCOUNT_FREEZED.getMessage());
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setUserId(user.getId());            // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setDeptId(user.getDeptId());    // 部门id
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptId()));// 部门名称
        shiroUser.setName(user.getName());        // 用户名称
        shiroUser.setPassword(user.getPassword());
        shiroUser.setStatus(UserStatusEnum.getValueByCode(user.getStatus()));
        shiroUser.setRoleId(user.getRoleId());
        Integer[] roleArray = ConvertUtils.toIntArray(user.getRoleId());// 角色集合
        List<Integer> roleList = new ArrayList<Integer>();
        List<String> roleNameList = new ArrayList<String>();
        for (int roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);
        return shiroUser;
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

    @Override
    public void checkAccount(String account) {
        User param = new User();
        param.setAccount(account);
        User user = userMapper.selectOne(param);
        if (user == null) {
            throw new ApiException(NO_THIS_USER.getCode(), NO_THIS_USER.getMessage());
        } else {
            throw new ApiException(HAD_THIS_USER.getCode(), HAD_THIS_USER.getMessage());
        }
    }


    @Override
    public void changeUserPwd(String account, String newPwd) {
        User param = new User();
        param.setAccount(account);
        User user = userMapper.selectOne(param);
        if (user != null) {
            String newMd5 = ShiroUtil.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            throw new ApiException(OLD_PWD_NOT_RIGHT.getCode(), OLD_PWD_NOT_RIGHT.getMessage());
        }
    }

    @Override
    public void insert(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setStatus(UserStatusEnum.OK.getCode());
        user.setName(userDto.getName());
        user.setDeptId(1);
        userMapper.insert(user);
    }

    @Override
    public void editUserStatus(Integer userId, String status) {
        if ("删除".equalsIgnoreCase(status)) {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                if (user.getStatus() == UserStatusEnum.LEAVE.getCode()) {
                    userMapper.setStatus(userId, UserStatusEnum.getCodeByValue(status));
                } else {
                    throw new ApiException(OLD_PWD_NOT_RIGHT.getCode(), "用户未离职不能删除");
                }

            }
        } else {
            userMapper.setStatus(userId, UserStatusEnum.getCodeByValue(status));
        }
    }


    @Override
    public void editUser(Integer userId, String name, String password) {
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setSalt(ShiroUtil.getRandomSalt(5));
        user.setPassword(ShiroUtil.md5(password, user.getSalt()));
        userMapper.updateByPrimaryKeySelective(user);
    }


}
