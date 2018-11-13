package com.one.pig.controller.user;

import com.one.pig.bean.dto.user.UserDto;
import com.one.pig.common.controller.BaseController;
import com.one.pig.common.persistence.dao.MenuMapper;
import com.one.pig.common.persistence.dao.UserMapper;
import com.one.pig.core.shiro.service.SessionService;
import com.one.pig.core.shiro.token.ShiroUser;
import com.one.pig.core.shiro.token.ShiroUtil;
import com.one.pig.core.shiro.token.manager.TokenManager;
import com.one.pig.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 登录控制器
 *
 * @author csy
 * @Date 2017年1月10日 下午8:25:24
 */
@RestController
@RequestMapping(value = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "LoginController", description = "登录登出相关")
@Validated
@CrossOrigin
public class LoginController extends BaseController {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SessionService sessionService;


    /**
     * 点击登录执行的动作
     */
    @ApiOperation(value = "登陆", notes = "入参：account 账号 password 密码  返回值和cookie里都有sessionID 所有接口放head里带过来", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login(@NotBlank(message = "账号不能为空") @RequestParam String account, @NotBlank(message = "密码不能为空") @RequestParam String password, @RequestParam String rememberMe, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
        if (rememberMe != null && "Y".equals(rememberMe)) {
            token.setRememberMe(true);
        }
        TokenManager.login(token);
        response.addHeader("sessionId", ShiroUtil.getSession().getId().toString());
        Map map = new HashMap();
        ShiroUser shiroUser = ShiroUtil.getUser();
        map.put("userId", shiroUser.getUserId());
        map.put("account", shiroUser.getAccount());
        map.put("name", shiroUser.getName());
        map.put("deptId", shiroUser.getDeptId());
        map.put("deptName", shiroUser.getDeptName());
        map.put("roleList", shiroUser.getRoleList());
        map.put("roleNames", shiroUser.getRoleNames());
        map.put("tips", ShiroUtil.getRoleTips());
        return null;
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "退出登录", notes = "", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @RequiresUser
    @ResponseBody
    public ResponseEntity logOut(UserDto userDto) {
        sessionService.selfLogout(String.valueOf(userDto.getUserId()));
        ShiroUtil.getSubject().logout();
        return null;
    }


}
