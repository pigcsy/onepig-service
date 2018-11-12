package com.one.pig.common.constant.dictmap.factory;

import com.one.pig.common.exception.BizExceptionEnum;
import com.one.pig.common.exception.BussinessException;
import com.one.pig.common.constant.dictmap.base.AbstractDictMap;
import com.one.pig.common.constant.dictmap.base.SystemDict;

/**
 * 字典的创建工厂
 *
 * @author fengshuonan
 * @date 2017-05-06 15:12
 */
public class DictMapFactory {

    private static final String basePath = "com.one.pig.common.constant.dictmap.";

    /**
     * 通过类名创建具体的字典类
     */
    public static AbstractDictMap createDictMap(String className) {
        if("SystemDict".equals(className)){
            return new SystemDict();
        }else{
            try {
                Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(basePath + className);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.ERROR_CREATE_DICT);
            }
        }
    }
}
