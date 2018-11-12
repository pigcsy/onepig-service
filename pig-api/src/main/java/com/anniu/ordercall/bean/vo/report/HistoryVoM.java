package com.anniu.ordercall.bean.vo.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 历史数据 Vo
 * -- 管理
 * @ClassName: HistoryVoM
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class HistoryVoM implements Serializable {

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
     * 客服量
     * -- 参与客服量
     */
    private Integer customerAmount;

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
     * 生成时间
     */
    private String generateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
