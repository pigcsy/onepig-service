package com.anniu.ordercall.bean.vo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 渠道商vo
 *
 * @author csy
 * @Date 2017/5/5 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessCountVo implements Serializable {

    private Double countSub;

    private Integer countModifyNum;


}
