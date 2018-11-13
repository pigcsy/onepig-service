package com.one.pig.controller.user;


import com.one.pig.bean.dto.user.UserDto;
import com.one.pig.common.annotion.Permission;
import com.one.pig.common.constant.Const;
import com.one.pig.common.constant.factory.ConstantFactory;
import com.one.pig.common.controller.BaseController;
import com.one.pig.common.node.ZTreeNode;
import com.one.pig.common.persistence.dao.MenuMapper;
import com.one.pig.core.log.LogObjectHolder;
import com.one.pig.core.util.common.ToolUtil;
import com.one.pig.service.user.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 菜单控制器
 *
 * @author csy
 * @Date 2017年2月12日21:59:14
 */
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "MenuController", description = "菜单")
@CrossOrigin
public class MenuController extends BaseController {


    @Resource
    MenuMapper menuMapper;

    @Autowired
    MenuService menuService;


    /**
     * 登陆用户获取菜单列表
     */
    @RequestMapping(value = "/list")
    @ApiOperation(value = "登陆用户获取菜单列表", notes = "登陆用户获取菜单列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequiresUser
    public ResponseEntity list(UserDto userDto) {
        return null;//ResponseEntity.success(menuService.getMenus(userDto));
    }


    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiOperation(value = "新增菜单", notes = "新增菜单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "String", paramType = "form"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequiresUser
    public ResponseEntity remove(@RequestParam Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw null;//new ApiException(REQUEST_NULL.getCode(), REQUEST_NULL.getMessage());
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));

        menuService.delMenuContainSubMenus(menuId);
        return null;
    }

    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "查看菜单", notes = "查看菜单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "String", paramType = "form"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequiresUser
    public ResponseEntity view(@RequestParam(required = false) Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            // throw new ApiException(REQUEST_NULL.getCode(), REQUEST_NULL.getMessage());
        }
        return null; //ResponseEntity.success(menuMapper.selectByPrimaryKey(menuId));
    }

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList", method = {RequestMethod.POST})
    @ApiOperation(value = "查看菜单", notes = "查看菜单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "String", paramType = "form"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequiresUser
    public ResponseEntity menuTreeList() {
        List<com.one.pig.common.node.ZTreeNode> roleTreeList = menuMapper.menuTreeList();
        return null;// ResponseEntity.success(roleTreeList);
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList", method = {RequestMethod.POST})
    @ApiOperation(value = "获取菜单列表(选择父级菜单用)", notes = "获取菜单列表(选择父级菜单用)", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequiresUser
    public ResponseEntity selectMenuTreeList() {
        List<com.one.pig.common.node.ZTreeNode> roleTreeList = menuMapper.menuTreeList();
        roleTreeList.add(com.one.pig.common.node.ZTreeNode.createParent());
        return null;// ResponseEntity.success(roleTreeList);
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByroleId", method = {RequestMethod.POST})
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "菜单id", required = true, dataType = "String", paramType = "form"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @RequiresUser
    public ResponseEntity menuTreeListByroleId(@RequestParam(required = false) Integer roleId) {
        List<Integer> menuIds =null;// menuMapper.getMenuIdsByroleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            List<com.one.pig.common.node.ZTreeNode> roleTreeList = menuMapper.menuTreeList();
            return null;// ResponseEntity.success(roleTreeList);
        } else {
            List<ZTreeNode> roleTreeListByUserId = menuMapper.menuTreeListByMenuIds(menuIds);
            return null; //ResponseEntity.success(roleTreeListByUserId);
        }
    }


}
