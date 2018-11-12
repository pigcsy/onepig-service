package com.anniu.ordercall.bean.param;

import com.anniu.ordercall.bean.annotation.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 状态信息 Param
 * @ClassName: StatusParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class StatusParam implements Serializable {

    /**
     * 主键Id
     */
    @Column(msg = "主键Id不允许为空")
    private Integer Id;

    /**
     * 状态
     * -- 0 下架.
     * -- 1 上架.
     */
    @Column(msg = "状态不允许为空")
    private Integer status;

}
