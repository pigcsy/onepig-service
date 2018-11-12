package com.anniu.ordercall.model.ordercall;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置信息
 * --（ 配置表 ）
 * @ClassName: Config
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class Config implements Serializable {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 名称
     * -- 配置名称
     */
    private String name;

    /**
     * 代码
     * -- 配置代码
     */
    private String code;

    /**
     * Value
     * -- 配置值
     */
    private String value;

    /**
     * 状态
     * -- 0 下架.
     * -- 1 上架.
     */
    private Integer status;

    /**
     * 排序权重
     */
    private Double sortPriority;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

}
