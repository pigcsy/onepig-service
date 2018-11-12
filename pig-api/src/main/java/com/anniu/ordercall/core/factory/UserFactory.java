package com.anniu.ordercall.core.factory;

import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.model.ordercall.User;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 *
 * @author csy
 * @date 2017-05-05 22:43
 */
public class UserFactory {

    public static User createUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        } else {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            return user;
        }
    }
}
