package com.anniu.ordercall.core.factory;


import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.core.base.AbstractDictMap;
import com.anniu.ordercall.core.base.SystemDict;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.ERROR_CREATE_DICT;

/**
 * 字典的创建工厂
 *
 * @author csy
 * @date 2017-05-06 15:12
 */
public class DictMapFactory {

    private static final String basePath = "com.anniu.ordercall.common.constant.dictmap.";

    /**
     * 通过类名创建具体的字典类
     */
    public static AbstractDictMap createDictMap(String className) {
        if ("SystemDict".equals(className)) {
            return new SystemDict();
        } else {
            try {
                Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(basePath + className);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new ApiException(ERROR_CREATE_DICT.getCode(), ERROR_CREATE_DICT.getMessage());
            }
        }
    }
}
