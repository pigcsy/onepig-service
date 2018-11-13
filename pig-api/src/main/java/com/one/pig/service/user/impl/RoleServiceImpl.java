package com.one.pig.service.user.impl;


import com.anniu.ordercall.service.user.IRoleService;
import com.one.pig.common.persistence.dao.RelationMapper;
import com.one.pig.common.persistence.dao.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // // 删除该角色所有的权限
        // this.roleMapper.deleteRolesById(roleId);
        //
        // // 添加新的权限
        // for (Integer id : ConvertUtils.toIntArray(ids)) {
        //     Relation relation = new Relation();
        //     relation.setRoleId(roleId);
        //     relation.setMenuId(id);
        //     this.relationMapper.insert(relation);
        // }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        // //删除角色
        // this.roleMapper.deleteByPrimaryKey(roleId);
        //
        // // 删除该角色所有的权限
        // this.roleMapper.deleteRolesById(roleId);
    }

}
