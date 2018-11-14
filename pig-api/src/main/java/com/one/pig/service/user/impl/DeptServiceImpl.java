package com.one.pig.service.user.impl;

import com.one.pig.service.user.IDeptService;
import com.one.pig.system.dao.DeptMapper;
import com.one.pig.system.model.Dept;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
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
