package com.anniu.ordercall.bean.vo.user;

import com.anniu.ordercall.bean.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

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
public class ServiceListVo implements Serializable {

    private List<UserDto> userDtoList;

    private Integer userNum;


}
