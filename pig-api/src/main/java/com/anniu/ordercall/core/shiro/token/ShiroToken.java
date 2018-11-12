package com.anniu.ordercall.core.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 开发公司：anniu在线工具 <p>
 * 版权所有：© www.anniu.com<p>
 * 博客地址：http://www.anniu.com/blog/  <p>
 * <p>
 * <p>
 * Shiro token
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　csy　2016年6月2日 　<br/>
 *
 * @author csy
 * @version 1.0, 2016年6月2日 <br/>
 * @email so@anniu.com
 */
@Data
public class ShiroToken extends UsernamePasswordToken implements java.io.Serializable {

    private static final long serialVersionUID = -6451794657814516274L;


    public ShiroToken(UsernamePasswordToken usernamePasswordToken) {
    }
}
