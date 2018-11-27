/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.one.pig.check;


import com.one.pig.ShiroUser;
import com.one.pig.ShiroUtil;
import com.one.pig.core.listener.ConfigListener;
import com.one.pig.core.util.common.SpringContextHolder;
import com.one.pig.core.util.support.CollectionKit;
import com.one.pig.core.util.support.HttpKit;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限自定义检查
 */
@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class PermissionCheckFactory implements ICheck {

    public static ICheck me() {
        return SpringContextHolder.getBean(ICheck.class);
    }

    @Override
    public boolean check(Object[] permissions) {
        ShiroUser user = ShiroUtil.getUser();
        if (null == user) {
            return false;
        }
        String join = CollectionKit.join(permissions, ",");
        if (ShiroUtil.hasAnyRoles(join)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAll() {
        HttpServletRequest request = HttpKit.getRequest();
        ShiroUser user = ShiroUtil.getUser();
        if (null == user) {
            return false;
        }
        String requestURI = request.getRequestURI().replace(ConfigListener.getConf().get("contextPath"), "");
        String[] str = requestURI.split("/");
        if (str.length > 3) {
            requestURI = "/" + str[1] + "/" + str[2];
        }
        if (ShiroUtil.hasPermission(requestURI)) {
            return true;
        }
        return false;
    }

}
