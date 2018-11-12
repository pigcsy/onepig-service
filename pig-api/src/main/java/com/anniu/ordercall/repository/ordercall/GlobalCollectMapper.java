package com.anniu.ordercall.repository.ordercall;

import com.anniu.ordercall.model.ordercall.GlobalCollect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 全局汇总 Mapper
 * --（ 全局汇总表 ）
 * @ClassName: GlobalCollectMapper
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Repository
public interface GlobalCollectMapper {

    /**
     * 保存(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    boolean saveEntity(@Param("param") GlobalCollect param);

    /**
     * 更新(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    boolean updateEntity(@Param("param") GlobalCollect param);

    /**
     * 删除(Entity)对象信息
     * @param id 主键Id
     * @return 处理结果
     */
    boolean deleteEntity(@Param("id") Integer id);

    /**
     * 获取(Entity)对象信息, 根据Id
     * @param id 主键Id
     * @return 处理结果
     */
    GlobalCollect obtainEntityById(@Param("id") Integer id);

    /**
     * 获取(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    List<GlobalCollect> obtainEntity(@Param("param") String param);

    /**
     * 获取(Entity)对象信息, 分页获取
     * @param param Sql信息
     * @return 处理结果
     */
    List<GlobalCollect> obtainEntityByPage(@Param("param") String param);

    /**
     * 获取(Entity)对象信息,统计记录数
     * @param param Sql信息
     * @return 处理结果
     */
    Integer obtainEntityCount(@Param("param") String param);

    /**
     * 获取(Entity)对象信息,根据自定义Sql
     * @param param Sql信息
     * @return 处理结果
     */
    List<GlobalCollect> obtainEntityByCustomizeSql(@Param("param") String param);

    /**
     * 获取(Entity)对象信息,统计记录数,根据自定义Sql
     * @param param Sql信息
     * @return 处理结果
     */
    Integer obtainEntityCountByCustomizeSql(@Param("param") String param);

}
