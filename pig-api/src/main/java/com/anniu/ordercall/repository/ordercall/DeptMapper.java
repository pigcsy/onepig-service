package com.anniu.ordercall.repository.ordercall;


import com.anniu.ordercall.bean.node.ZTreeNode;
import com.anniu.ordercall.model.ordercall.Dept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author daze
 * @since 2017-07-11
 */
@Repository
public interface DeptMapper extends Mapper<Dept> {
    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);
}