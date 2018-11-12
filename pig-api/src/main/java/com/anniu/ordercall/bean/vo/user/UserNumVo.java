package com.anniu.ordercall.bean.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户传输bean
 *
 * @author csy
 * @Date 2017/5/5 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserNumVo implements Serializable {

    public Integer normalNum;

    public Integer freezeNum;

    private Integer leaveNum;


}
