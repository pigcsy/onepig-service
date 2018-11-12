package com.anniu.ordercall.model.ordercall;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 汇总信息
 * --（ 汇总表 ）
 * @ClassName: Collect
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class Collect implements Serializable {

    /**
     * 主键Id
     */
    private Integer id;

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
     * 客服量
     * -- 参与客服量
     */
    private Integer customerAmount;

    /**
     * 支付金额
     * -- 转化支付金额
     */
    private Double paymentAmount;

    /**
     * 接通率
     */
    private Double connectRate;

    /**
     * 沟通率
     * -- 有效沟通率
     */
    private Double commRate;

    /**
     * 沟通率
     * -- 有效沟通率(全局)
     */
    private Double globalCommRate;

    /**
     * 转化率
     * -- 用户转化率
     */
    private Double convertRate;

    /**
     * 平均时间
     * -- 平均处理时间（ 秒 ）
     */
    private Integer averageTime;

    /**
     * 累计时间
     * -- 累计处理时间（ 秒 ）
     */
    private Integer totalTime;

    /**
     * 平均时间
     * -- 平均处理时间（ 客服 : 分 ）
     */
    private Double customerTime;

    /**
     * 平均量
     * -- 平均处理量(客服)
     */
    private Integer averageAmount;

    /**
     * 生成时间
     */
    private String generateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

}
