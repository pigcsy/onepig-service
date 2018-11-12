package com.anniu.ordercall.repository.ordercall;

import com.anniu.ordercall.bean.dto.report.GlobalDto;
import com.anniu.ordercall.bean.vo.report.UserVo;
import com.anniu.ordercall.model.ordercall.Collect;
import com.anniu.ordercall.model.ordercall.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报告信息 Mapper
 * --（ 报告表 ）
 * @ClassName: ReportMapper
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Repository
public interface ReportMapper {

    /**
     * 保存(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    boolean saveEntity(@Param("param") Report param);

    /**
     * 更新(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    boolean updateEntity(@Param("param") Report param);

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
    Report obtainEntityById(@Param("id") Integer id);

    /**
     * 获取(Entity)对象信息
     * @param param 对象信息
     * @return 处理结果
     */
    List<Report> obtainEntity(@Param("param") String param);

    /**
     * 获取(Entity)对象信息, 分页获取
     * @param param Sql信息
     * @return 处理结果
     */
    List<Report> obtainEntityByPage(@Param("param") String param);

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
    List<Report> obtainEntityByCustomizeSql(@Param("param") String param);

    /**
     * 获取(Entity)对象信息,统计记录数,根据自定义Sql
     * @param param Sql信息
     * @return 处理结果
     */
    Integer obtainEntityCountByCustomizeSql(@Param("param") String param);


    /**
     * 汇总报表
     * -- 汇总数据
     * -- 管理
     * @param param Sql信息
     * @return 处理结果
     */
    Collect collectReportM(@Param("param") String param);

    /**
     * 汇总报表
     * -- 实时数据
     * -- 管理
     * @param param Sql信息
     * @return 处理结果
     */
    Collect collectRealTimeM(@Param("param") String param);

    /**
     * 汇总报表
     * -- 汇总数据
     * -- 客服
     * @param param Sql信息
     * @return 处理结果
     */
    List<Report> collectReportC(@Param("param") String param);

    /**
     * 汇总报表
     * -- 全局汇总
     * -- 客服
     * @return 处理结果
     */
    List<GlobalDto> collectGlobalC();

    /**
     * 获取信息
     * -- 客服信息
     * @return 处理结果
     */
    List<UserVo> obtainCustomer();

}
