package com.one.pig.system.dao;

import com.one.pig.core.base.MyMapper;
import com.one.pig.system.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
@Mapper
public interface UserMapper extends MyMapper<User> {


}