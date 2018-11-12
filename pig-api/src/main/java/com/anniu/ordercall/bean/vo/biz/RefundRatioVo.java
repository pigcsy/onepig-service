package com.anniu.ordercall.bean.vo.biz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefundRatioVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer todayRefund;
	
	private Integer paySuccess;
	
	private Integer totalRefund;
	
	private Integer payUsers;
	
	private Integer payOrders;
	
	private Integer calls;
}
