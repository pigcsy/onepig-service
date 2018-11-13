package com.one.pig.bean.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 * @ClassName: Column
 * @Target 注解设置该注解用于什么声明(FIELD : 字段或枚举的常量)
 * @Retention 注解设置VM将在运行期也保留该注释, 因此可以通过反射机制读取注解的信息
 * @Documented 注解设置此注解包含在javadoc中
 * @Inherited 注解设置允许子类继承父类中的注解
 * @DetaTime 2018-02-23 09:57:32
 * @author 花花
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {

    /**
     * 提示消息
     * --- 默认值 ""
     */
    String msg() default "缺少参数";

}
