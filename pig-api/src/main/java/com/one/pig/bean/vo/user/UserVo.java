package com.one.pig.bean.vo.user;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class UserVo implements Serializable {
    /**
     * 角色名称集
     */
    @ApiParam(hidden = true)
    public List<String> roleNames;
    /**
     * 角色集
     */
    @ApiParam(hidden = true)
    public List<Integer> roleList;
    /**
     *
     */
    @ApiParam(hidden = true)
    private Integer userId;
    /**
     * 账号
     */
    @ApiParam(hidden = true)
    private String account;
    /**
     * 密码
     */
    @ApiParam(hidden = true)
    private String password;
    /**
     * md5密码盐
     */
    @ApiParam(hidden = true)
    private String salt;
    /**
     * 名字
     */
    @ApiParam(hidden = true)
    private String name;
    /**
     * 角色id
     */
    @ApiParam(hidden = true)
    private String roleId;
    /**
     * 部门id
     */
    @ApiParam(hidden = true)
    private Integer deptId;
    /**
     * 状态(1：启用  2：冻结  3：离职 4：删除）
     */
    @ApiParam(hidden = true)
    private Integer status;


    private String userStatus;

    private Date createDate;

    private String registerTime;


}
