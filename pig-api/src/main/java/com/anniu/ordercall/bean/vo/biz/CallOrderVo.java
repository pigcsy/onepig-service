package com.anniu.ordercall.bean.vo.biz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 有借接口vo
 *
 * @author csy
 * @Date 2017/5/5 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CallOrderVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userName;

    private String orderTime;

    private Date orderTimeDate;

    private Date beginTimeDate;

    private String beginTime;

    private Integer dealTime;

    private String dealResult;

    private Integer dealResultInteger;


}
