package com.one.pig.controller.user;

import com.one.pig.common.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    //
    // @Autowired
    // MenuMapper menuMapper;
    //
    // @Autowired
    // UserService userService;
    //
    // @Autowired
    // UserMapper userMapper;
    //
    //
    //
    // /**
    //  * 点击登录执行的动作
    //  */
    // @ApiOperation(value = "登陆", notes = "入参：account 账号 password 密码  返回值和cookie里都有sessionID 所有接口放head里带过来", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @ResponseBody
    // @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // public ResponseEntity login(@NotBlank(message = "账号不能为空") @RequestParam String account, @NotBlank(message = "密码不能为空") @RequestParam String password, @RequestParam String rememberMe, HttpServletRequest request, HttpServletResponse response) {
    //     UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
    //     if (rememberMe != null && "Y".equals(rememberMe)) {
    //         token.setRememberMe(true);
    //     }
    //     ShiroUtil.getSubject().login(token);
    //     response.addHeader("sessionId", ShiroUtil.getSession().getId().toString());
    //     Map map = new HashMap();
    //     ShiroUser shiroUser = ShiroUtil.getUser();
    //     map.put("userId", shiroUser.getId());
    //     map.put("account", shiroUser.getAccount());
    //     map.put("name", shiroUser.getName());
    //     map.put("deptId", shiroUser.getDeptId());
    //     map.put("deptName", shiroUser.getDeptName());
    //     map.put("roleList", shiroUser.getRoleList());
    //     map.put("roleNames", shiroUser.getRoleNames());
    //     return null;
    // }
    //
    // /**
    //  * 退出登录
    //  */
    // @ApiOperation(value = "退出登录", notes = "", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @RequestMapping(value = "/logout", method = RequestMethod.GET)
    // @RequiresUser
    // @ResponseBody
    // public ResponseEntity logOut(UserDto userDto) {
    //     LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroUtil.getUser().getId(), getIp()));
    //     ShiroUtil.getSubject().logout();
    //     return null;
    // }


}
