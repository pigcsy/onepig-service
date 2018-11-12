package com.anniu.ordercall.core.factory;


import com.anniu.mid.freework.container.spring.web.exception.ApiException;

import java.lang.reflect.Method;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.ERROR_WRAPPER_FIELD;

/**
 * 字段的包装创建工厂
 *
 * @author csy
 * @date 2017-05-06 15:12
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object field, String methodName) {
        IConstantFactory me = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, field.getClass());
            Object result = method.invoke(me, field);
            return result;
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                Object result = method.invoke(me, Integer.parseInt(field.toString()));
                return result;
            } catch (Exception e1) {
                throw new ApiException(ERROR_WRAPPER_FIELD.getCode(), ERROR_WRAPPER_FIELD.getMessage());
            }
        }
    }

}
