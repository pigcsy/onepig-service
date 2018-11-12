package com.anniu.ordercall.repository.ordercall;

import com.anniu.ordercall.model.ordercall.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 配置信息 Mapper
 * --（ 配置表 ）
 * @ClassName: ConfigMapper
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Repository
public interface ConfigMapper {

    /**
     * 保存(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    boolean saveEntity(@Param("param") Config param);

    /**
     * 更新(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    boolean updateEntity(@Param("param") Config param);

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
    Config obtainEntityById(@Param("id") Integer id);

    /**
     * 获取(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    List<Config> obtainEntity(@Param("param") String param);

    /**
     * 获取(Entity)对象信息, 分页获取
     * @param param Sql信息
     * @return 处理结果
     */
    List<Config> obtainEntityByPage(@Param("param") String param);

    /**
     * 获取(Entity)对象信息, 统计记录数
     * @param param Sql信息
     * @return 处理结果
     */
    Integer obtainEntityCount(@Param("param") String param);

    /**
     * 获取(Entity)对象信息, 根据自定义Sql
     * @param param Sql信息
     * @return 处理结果
     */
    List<Config> obtainEntityByCustomizeSql(@Param("param") String param);

    /**
     * 获取(Entity)对象信息, 统计记录数, 根据自定义Sql
     * @param param Sql信息
     * @return 处理结果
     */
    Integer obtainEntityCountByCustomizeSql(@Param("param") String param);

    /**
     *
     * @param code
     * @return
     */
    Config selectValueByCode(String code);
}
