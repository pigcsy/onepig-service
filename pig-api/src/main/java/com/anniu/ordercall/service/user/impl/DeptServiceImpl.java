package com.anniu.ordercall.service.user.impl;


import com.anniu.ordercall.model.ordercall.Dept;
import com.anniu.ordercall.repository.ordercall.DeptMapper;
import com.anniu.ordercall.service.user.IDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements IDeptService {

    @Resource
    DeptMapper deptMapper;

    @Override
    public void deleteDept(Integer deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        Example example = new Example(Dept.class);
        example.createCriteria().andLike("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectByExample(example);
        for (Dept temp : subDepts) {
            deptMapper.deleteByPrimaryKey(temp);
        }
        deptMapper.deleteByPrimaryKey(deptId);
    }
}
