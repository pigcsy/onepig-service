package com.one.pig.modular.system.warpper;

import com.one.pig.common.warpper.BaseControllerWarpper;
import com.one.pig.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class UserWarpper extends BaseControllerWarpper {

    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
    }

}
