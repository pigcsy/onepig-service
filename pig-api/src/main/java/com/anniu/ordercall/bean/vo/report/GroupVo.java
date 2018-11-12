package com.anniu.ordercall.bean.vo.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分组信息 Vo
 * -- 实时数据
 * @ClassName: GroupVo
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class GroupVo implements Serializable {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 订单数
     * -- 有效订单
     */
    private Integer orderNum;

    /**
     * 订单金额
     */
    private Double orderAmount;

}
