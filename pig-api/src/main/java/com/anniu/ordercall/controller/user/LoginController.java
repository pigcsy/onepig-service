package com.anniu.ordercall.controller.user;


import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.core.base.BaseController;
import com.anniu.ordercall.core.shiro.service.SessionService;
import com.anniu.ordercall.core.shiro.token.ShiroUser;
import com.anniu.ordercall.core.shiro.token.ShiroUtil;
import com.anniu.ordercall.core.shiro.token.manager.TokenManager;
import com.anniu.ordercall.repository.ordercall.MenuMapper;
import com.anniu.ordercall.repository.ordercall.UserMapper;
import com.anniu.ordercall.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
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
    public ResponseEntity loginVali(@NotBlank(message = "账号不能为空") @RequestParam String account, @NotBlank(message = "密码不能为空") @RequestParam String password, @RequestParam String rememberMe, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
        // if (!userService.checkIp(request)) {
        //     throw new ApiException(NO_WHITE_IP.getCode(), NO_WHITE_IP.getMessage());
        //
        // }
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
        return ResponseEntity.success(map);
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
        return ResponseEntity.success();
    }


}
