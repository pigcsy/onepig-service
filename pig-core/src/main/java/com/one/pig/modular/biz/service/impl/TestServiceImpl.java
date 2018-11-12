package com.one.pig.modular.biz.service.impl;

import com.one.pig.common.annotion.DataSource;
import com.one.pig.common.constant.DSEnum;
import com.one.pig.common.persistence.dao.TestMapper;
import com.one.pig.common.persistence.model.Test;
import com.one.pig.modular.biz.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试服务
 *
 * @author fengshuonan
 * @date 2017-06-23 23:02
 */
@Service
public class TestServiceImpl implements ITestService {


    @Autowired
    TestMapper testMapper;

    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_BIZ)
    public void testBiz() {
        Test test = testMapper.selectByPrimaryKey(1);
        test.setId(22);
        testMapper.insert(test);
    }


    @Override
    @DataSource(name = DSEnum.DATA_SOURCE_PIG)
    public void testpig() {
        Test test = testMapper.selectByPrimaryKey(1);
        test.setId(33);
        testMapper.insert(test);
    }

    @Override
    @Transactional
    public void testAll() {
        testBiz();
        testpig();
        //int i = 1 / 0;
    }

}
