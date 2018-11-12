package com.anniu.ordercall.bean.vo.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 客服信息 Vo
 * -- 实时数据
 * -- Redis
 * @ClassName: RedisVo
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class RedisVo implements Serializable {

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

    /**
     * 客服主键
     * -- 客服Id
     */
    private Integer serviceId;

}
