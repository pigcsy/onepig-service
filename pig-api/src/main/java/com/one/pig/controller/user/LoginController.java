package com.one.pig.controller.user;

import com.one.pig.common.controller.BaseController;
import com.one.pig.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录控制器
 *
 * @author csy
 * @Date 2017年1月10日 下午8:25:24
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "LoginController", description = "登录登出相关")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 点击登录执行的动作
     */
    @ApiOperation(value = "登陆", notes = "入参：account 账号 password 密码  返回值和cookie里都有sessionID 所有接口放head里带过来", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity login() {
        userService.test();
        return null;
    }


}
