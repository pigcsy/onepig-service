package com.anniu.ordercall.service.user.impl;


import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.bean.dto.common.MailDto;
import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.bean.vo.user.UserNumVo;
import com.anniu.ordercall.bean.vo.user.UserVo;
import com.anniu.ordercall.core.base.SpringContextHolder;
import com.anniu.ordercall.core.enums.UserStatusEnum;
import com.anniu.ordercall.core.redis.RedisCacheManager;
import com.anniu.ordercall.core.shiro.token.ShiroUser;
import com.anniu.ordercall.core.shiro.token.ShiroUtil;
import com.anniu.ordercall.core.utils.ConvertUtils;
import com.anniu.ordercall.core.utils.DateUtils;
import com.anniu.ordercall.core.utils.IpUtils;
import com.anniu.ordercall.core.utils.MathUtils;
import com.anniu.ordercall.model.ordercall.IpAllow;
import com.anniu.ordercall.model.ordercall.User;
import com.anniu.ordercall.repository.ordercall.IpAllowMapper;
import com.anniu.ordercall.repository.ordercall.MenuMapper;
import com.anniu.ordercall.repository.ordercall.UserMapper;
import com.anniu.ordercall.service.user.SendMail;
import com.anniu.ordercall.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.anniu.ordercall.core.constant.Cache.SUBCOMB_EMAIL_CODE;
import static com.anniu.ordercall.core.constant.CommonConstant.I_ONE;
import static com.anniu.ordercall.core.constant.CommonConstant.I_THREE;
import static com.anniu.ordercall.core.constant.CommonConstant.I_TOW;
import static com.anniu.ordercall.core.exception.BizExceptionEnum.*;
import static com.anniu.ordercall.core.utils.DateUtils.YYYY_MM_DD;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private IpAllowMapper ipAllowMapper;

    @Autowired
    private RedisCacheManager redisCacheManager;


    @Value("${email.sendFrom}") // 收件人
    private String sendFrom;
    @Value("${email.host}")  // SMTP主机
    private String host;
    @Value("${email.username}") // 发件人的用户名
    private String username;
    @Value("${email.password}") // 发件人的密码
    private String password;

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
        // 账号被冻结
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
        shiroUser.setDeptName(com.anniu.ordercall.core.factory.ConstantFactory.me().getDeptName(user.getDeptId()));// 部门名称
        shiroUser.setName(user.getName());        // 用户名称
        shiroUser.setPassword(user.getPassword());
        shiroUser.setStatus(UserStatusEnum.getValueByCode(user.getStatus()));
        shiroUser.setRoleId(user.getRoleId());
        Integer[] roleArray = ConvertUtils.toIntArray(user.getRoleId());// 角色集合
        List<Integer> roleList = new ArrayList<Integer>();
        List<String> roleNameList = new ArrayList<String>();
        for (int roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(com.anniu.ordercall.core.factory.ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);
        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByroleId(Integer roleId) {
        List<String> resUrls = menuMapper.getResUrlsByroleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByroleId(Integer roleId) {
        return com.anniu.ordercall.core.factory.ConstantFactory.me().getSingleRoleTip(roleId);
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
    @Async
    public void sendEmail(String account) throws Exception {
        MailDto mb = new MailDto();
        SendMail sm = new SendMail();
        String verificationCode = MathUtils.getRandom620(6);
        mb.setTo(account);
        mb.setSubject("外呼平台修改密码验证码");
        mb.setContent(verificationCode);
        mb.setHost(host);
        mb.setFrom(sendFrom);
        mb.setUsername(username);
        mb.setPassword(password);
        try {
            sm.sendMail(mb);
        } catch (Exception e) {
            throw new ApiException(SEND_EMAIL_ERROR.getCode(), SEND_EMAIL_ERROR.getMessage());
        }
        redisCacheManager.put(SUBCOMB_EMAIL_CODE + account, verificationCode, 600);
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
    public PageInfo<UserVo> serviceList(Integer pageNum, Integer pageSize, String status) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserVo> userList = userMapper.serviceList(UserStatusEnum.getCodeByValue(status));
        userList.stream().map(target -> {
            target.setRegisterTime(DateUtils.formatDate(target.getCreateDate(), YYYY_MM_DD));
            target.setUserStatus(UserStatusEnum.getValueByCode(target.getStatus()));
            return target;
        }).collect(Collectors.toList());
        PageInfo<UserVo> channelPageInfo = new PageInfo<>(userList);
        return channelPageInfo;
    }

    @Override
    public boolean checkIp(HttpServletRequest request) {
        boolean checkStatus = false;
        String ip = IpUtils.getIpFromRequest(request);
        List<IpAllow> ipAllowList = ipAllowMapper.selectAll();
        for (IpAllow ipAllow : ipAllowList) {
            if (ip.equalsIgnoreCase(ipAllow.getIp()))
                checkStatus = true;
        }
        return checkStatus;
    }

    @Override
    public UserNumVo serviceNum() {
        Example normalNumExample = new Example(User.class);
        normalNumExample.createCriteria().andCondition("status = ", I_ONE).andCondition("role_id = ", I_TOW);
        List<User> normalNumList = userMapper.selectByExample(normalNumExample);
        Example freezeNumExample = new Example(User.class);
        freezeNumExample.createCriteria().andCondition("status = ", I_TOW);
        List<User> freezeNumList = userMapper.selectByExample(freezeNumExample);
        Example leaveNumExample = new Example(User.class);
        leaveNumExample.createCriteria().andCondition("status = ", I_THREE);
        List<User> leaveNumList = userMapper.selectByExample(leaveNumExample);
        UserNumVo userNumVo = new UserNumVo();
        userNumVo.setNormalNum(normalNumList.size());
        userNumVo.setFreezeNum(freezeNumList.size());
        userNumVo.setLeaveNum(leaveNumList.size());
        return userNumVo;
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
