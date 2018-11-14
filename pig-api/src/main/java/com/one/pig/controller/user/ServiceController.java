package com.one.pig.controller.user;


import com.one.pig.common.controller.BaseController;
import com.one.pig.core.redis.RedisCacheManager;
import com.one.pig.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统管理员控制器c
 *
 * @author csy
 * @Date 2017年1月11日 下午1:08:17
 */
@RestController
@RequestMapping(value = "/service-manage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "ServiceController", description = "用户")
@CrossOrigin
public class ServiceController extends BaseController {

    // @Resource
    // private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Autowired
    private RedisCacheManager redisCacheManager;
    //
    //
    // /**
    //  * 检测账号是否存在
    //  */
    // @RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
    // @ApiOperation(value = "检测账号是否存在", notes = "检测账号是否存在", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String", paramType = "form"),
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @ResponseBody
    // public ResponseEntity checkAccount(@RequestParam String account) {
    //     userService.checkAccount(account);
    //     return null;//ResponseEntity.success();
    // }
    //
    // /**
    //  * 修改用户的密码邮件
    //  */
    // @RequestMapping(value = "/sendEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ResponseBody
    // @ApiOperation(value = "发送修改用户的密码邮件", notes = "发送修改用户的密码邮件", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String", paramType = "form"),
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // public ResponseEntity sendEmail(@RequestParam String account) {
    //     try {
    //         userService.sendEmail(account);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;//ResponseEntity.success();
    // }
    //
    // /**
    //  * 修改当前用户的密码
    //  */
    // @RequestMapping(value = "/changeUserPwd", method = RequestMethod.POST)
    // @ApiOperation(value = "修改用户密码", notes = "修改用户的密码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String", paramType = "form"),
    //         @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, dataType = "String", paramType = "form"),
    //         @ApiImplicitParam(name = "newPwd", value = "新密码", required = true, dataType = "String", paramType = "form"),
    //         @ApiImplicitParam(name = "rePwd", value = "确认的新密码", required = true, dataType = "String", paramType = "form"),
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // public ResponseEntity changeUserPwd(@RequestParam String account, @RequestParam String verificationCode, @RequestParam String newPwd, @RequestParam String rePwd) {
    //     String oldcode = null;//redisCacheManager.get(SUBCOMB_EMAIL_CODE + account);
    //     if (!newPwd.equals(rePwd)) {
    //         // throw new ApiException(TWO_PWD_NOT_MATCH.getCode(), TWO_PWD_NOT_MATCH.getMessage());
    //     }
    //     if (oldcode == null && !verificationCode.equalsIgnoreCase(oldcode)) {
    //         // throw new ApiException(INVALID_KAPTCHA.getCode(), INVALID_KAPTCHA.getMessage());
    //     }
    //     userService.changeUserPwd(account, newPwd);
    //     return null;// ResponseEntity.success();
    // }
    //
    // /**
    //  * 修改客服信息
    //  */
    // @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    // @ApiOperation(value = "修改客服信息", notes = "修改客服信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "userId", value = "", required = true, dataType = "int", paramType = "form"),
    //         @ApiImplicitParam(name = "password", value = "账号", required = true, dataType = "String", paramType = "form"),
    //         @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String", paramType = "form")
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // public ResponseEntity editUser(@RequestParam Integer userId, @RequestParam String password, @RequestParam String name) {
    //     userService.editUser(userId, password, name);
    //     return null;// ResponseEntity.success();
    // }
    //
    // /**
    //  * 添加各种用户
    //  */
    // @RequestMapping(value = "/add", method = RequestMethod.POST)
    // @Permission(Const.ADMIN_NAME)
    // @ResponseBody
    // @ApiOperation(value = "添加各种权限用户", notes = "roleId 2：客服 ", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @RequiresUser
    // public ResponseEntity add(@Validated @RequestBody UserDto user) {
    //     // 判断账号是否重复
    //     User theUser = userMapper.getByAccount(user.getAccount());
    //     if (theUser != null) {
    //         // throw new ApiException(USER_ALREADY_REG.getCode(), USER_ALREADY_REG.getMessage());
    //     }
    //     // 完善账号信息
    //     user.setSalt(ShiroUtil.getRandomSalt(5));
    //     user.setPassword(ShiroUtil.md5(user.getPassword(), user.getSalt()));
    //     user.setCreateDate(new Date());
    //     userService.insert(user);
    //     return null;//ResponseEntity.success();
    // }
    //
    //
    // /**
    //  * 获取当前登录用户信息
    //  */
    // @ApiOperation(value = "获取当前登录用户信息", notes = "", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @ResponseBody
    // @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    // @RequiresUser
    // public ResponseEntity userInfo() {
    //     ShiroUser shiroUser = ShiroUtil.getUser();
    //     super.getSession().setAttribute("shiroUser", shiroUser);
    //     Map map = new HashMap();
    //     ShiroUtil.getSession().getId().toString();
    //     map.put("sessionId", ShiroUtil.getSession().getId().toString());
    //     map.put("name", shiroUser.getName());
    //     map.put("id", shiroUser.getUserId());
    //     map.put("account", shiroUser.getAccount());
    //     return null;//ResponseEntity.success(map);
    // }
    //
    // /**
    //  * 用户列表
    //  */
    // @ApiOperation(value = "用户列表", notes = "", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "status", value = "用户状态： 正常 冻结 离职 删除  ", required = true, dataType = "string", paramType = "form"),
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @ResponseBody
    // @RequestMapping(value = "/serviceList", method = RequestMethod.GET)
    // @RequiresUser
    // public ResponseEntity serviceList(Integer pageNum, Integer pageSize, @RequestParam String status) {
    //     return null;//ResponseEntity.success(userService.serviceList(pageNum, pageSize, status));
    // }
    //
    // /**
    //  * 用户个数
    //  */
    // @ApiOperation(value = "用户个数", notes = "", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @ResponseBody
    // @RequestMapping(value = "/serviceNum", method = RequestMethod.GET)
    // @RequiresUser
    // public ResponseEntity serviceNum() {
    //     return null;//ResponseEntity.success(userService.serviceNum());
    // }
    //
    // /**
    //  * 操作用户账号状态
    //  */
    // @RequestMapping("/edit-user-status")
    // @ApiOperation(value = "修改用户状态", notes = "", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @ApiImplicitParams({
    //         @ApiImplicitParam(name = "userId", value = "要修改的用户id", required = true, dataType = "Integer", paramType = "form"),
    //         @ApiImplicitParam(name = "status", value = "用户状态： 正常 冻结 离职 删除 ", required = true, dataType = "string", paramType = "form"),
    // })
    // @ApiResponses(value = {
    //         @ApiResponse(code = 0, message = "success")
    // })
    // @BussinessLog(value = "修改用户状态", key = "userId", dict = Dict.UserDict)
    // @Permission(Const.ADMIN_NAME)
    // @ResponseBody
    // public ResponseEntity editUserStatus(@RequestParam Integer userId, @RequestParam String status) {
    //     if (ToolUtil.isEmpty(userId)) {
    //         // throw new ApiException(DELETE_NULL.getCode(), DELETE_NULL.getMessage());
    //     }
    //     //不能冻结超级管理员
    //     if (userId.equals(Const.ADMIN_ID)) {
    //         // throw new ApiException(CANT_FREEZE_ADMIN.getCode(), CANT_FREEZE_ADMIN.getMessage());
    //     }
    //     assertAuth(userId);
    //     userService.editUserStatus(userId, status);
    //     return null;//ResponseEntity.success();
    // }
    //
    // /**
    //  * 判断当前登录的用户是否有操作这个用户的权限
    //  */
    // private ResponseEntity assertAuth(Integer userId) {
    //     List<Integer> deptDataScope = ShiroUtil.getDeptDataScope();
    //     User user = userMapper.selectByPrimaryKey(userId);
    //     // Integer deptid = user.getDeptId();
    //     // if (deptDataScope.contains(deptid)) {
    //     //     return ResponseEntity.success();
    //     // } else {
    //     //     throw new ApiException(NO_PERMITION.getCode(), NO_PERMITION.getMessage());
    //     // }
    //     return null;
    //
    // }


}
