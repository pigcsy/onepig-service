package com.anniu.ordercall.repository.ordercall;

import com.anniu.ordercall.model.ordercall.Relation;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2017-07-11
 */
@Repository
public interface RelationMapper extends Mapper<Relation> {

}