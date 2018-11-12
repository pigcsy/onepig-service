package com.anniu.ordercall.core.factory;


import com.anniu.ordercall.core.base.SpringContextHolder;
import com.anniu.ordercall.core.enums.UserStatusEnum;
import com.anniu.ordercall.core.utils.ConvertUtils;
import com.anniu.ordercall.core.utils.StrUtils;
import com.anniu.ordercall.core.utils.ToolUtils;
import com.anniu.ordercall.model.ordercall.Dept;
import com.anniu.ordercall.model.ordercall.Dict;
import com.anniu.ordercall.model.ordercall.Menu;
import com.anniu.ordercall.model.ordercall.Role;
import com.anniu.ordercall.model.ordercall.User;
import com.anniu.ordercall.repository.ordercall.DeptMapper;
import com.anniu.ordercall.repository.ordercall.DictMapper;
import com.anniu.ordercall.repository.ordercall.MenuMapper;
import com.anniu.ordercall.repository.ordercall.RoleMapper;
import com.anniu.ordercall.repository.ordercall.UserMapper;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量的生产工厂
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author csy
     * @Date 2017/5/9 23:41
     */
    @Override
    public String getUserNameById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author csy
     * @date 2017年5月16日21:55:371
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    public String getRoleName(String roleIds) {
        Integer[] roles = ConvertUtils.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectByPrimaryKey(role);
            if (ToolUtils.isNotEmpty(roleObj) && ToolUtils.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrUtils.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectByPrimaryKey(roleId);
        if (ToolUtils.isNotEmpty(roleObj) && ToolUtils.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectByPrimaryKey(roleId);
        if (ToolUtils.isNotEmpty(roleObj) && ToolUtils.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        if (ToolUtils.isNotEmpty(dept) && ToolUtils.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = ConvertUtils.toIntArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            Menu menuObj = menuMapper.selectByPrimaryKey(menu);
            if (ToolUtils.isNotEmpty(menuObj) && ToolUtils.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrUtils.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Integer menuId) {
        if (ToolUtils.isEmpty(menuId)) {
            return "";
        } else {
            Menu menu = menuMapper.selectByPrimaryKey(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtils.isEmpty(code)) {
            return "";
        } else {
            Menu param = new Menu();
            param.setCode(code);
            Menu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Integer dictId) {
        if (ToolUtils.isEmpty(dictId)) {
            return "";
        } else {
            Dict dict = dictMapper.selectByPrimaryKey(dictId);
            if (dict == null) {
                return "";
            } else {
                return dict.getName();
            }
        }
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
        Dict temp = new Dict();
        temp.setName(name);
        Dict dict = dictMapper.selectOne(temp);
        if (dict == null) {
            return "";
        } else {
            Example example = new Example(Dict.class);
            example.createCriteria().andEqualTo("pid", dict.getId());
            List<Dict> dicts = dictMapper.selectByExample(example);
            for (Dict item : dicts) {
                if (item.getNum() != null && item.getNum().equals(val)) {
                    return item.getName();
                }
            }
            return "";
        }
    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", sex);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return UserStatusEnum.getValueByCode(status);
    }


    /**
     * 获取子部门id
     */
    @Override
    public List<Integer> getSubDeptId(Integer deptid) {
        Example example = new Example(Dept.class);
        example.createCriteria().andLike("pids", "%[" + deptid + "]%");

        List<Dept> depts = deptMapper.selectByExample(example);

        ArrayList<Integer> deptids = new ArrayList<>();

        if (depts != null || depts.size() > 0) {
            for (Dept dept : depts) {
                deptids.add(dept.getId());
            }
        }

        return deptids;
    }

    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(Integer id) {
        if (ToolUtils.isEmpty(id)) {
            return null;
        } else {
            Example example = new Example(Dict.class);
            example.createCriteria().andEqualTo("pid", id);
            List<Dict> dicts = dictMapper.selectByExample(example);
            if (dicts == null || dicts.size() == 0) {
                return null;
            } else {
                return dicts;
            }
        }
    }

    /**
     * 获取所有父部门id
     */
    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        Dept dept = deptMapper.selectByPrimaryKey(deptid);
        String pids = dept.getPids();
        String[] split = pids.split(",");
        ArrayList<Integer> parentDeptIds = new ArrayList<>();
        for (String s : split) {
            parentDeptIds.add(Integer.valueOf(StrUtils.removeSuffix(StrUtils.removePrefix(s, "["), "]")));
        }
        return parentDeptIds;
    }


}
