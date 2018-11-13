package com.one.pig.bean.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 全局汇总 Dto
 * @ClassName: GlobalDto
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class GlobalDto implements Serializable {

    /**
     * 天数
     */
    private Integer dailyAmount;

    /**
     * 处理量
     * -- 处理任务量
     */
    private Integer handleAmount;

    /**
     * 接通量
     * -- 客户接通量
     */
    private Integer connectAmount;

    /**
     * 沟通量
     * -- 有效沟通量
     */
    private Integer commAmount;

    /**
     * 支付量
     * -- 转化支付量
     */
    private Integer payAmount;

    /**
     * 用户量
     * -- 支付用户量
     */
    private Integer userAmount;

    /**
     * 支付金额
     * -- 转化支付金额
     */
    private Double paymentAmount;

    /**
     * 累计时间
     * -- 累计处理时间（ 客服 : 秒 ）
     */
    private Integer totalDateTime;

    /**
     * 登录用户
     * -- 用户主键(客服)
     */
    private Integer userId;

}
