package com.one.pig.core.util.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 开发公司：anniu.com<br/>
 * 版权：anniu.com<br/>
 * <p>
 * <p>
 * 静态获取Bean
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　csy　2016年4月4日 　<br/>
 * <p>
 * *******
 * <p>
 *
 * @author csy
 * @version 1.0, 2016年4月4日 <br/>
 * @email i@anniu.com
 */
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 实现
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) throws BeansException {
        try {
            return applicationContext.getBean(name);
        } catch (Exception e) {
            throw new RuntimeException("获取的Bean不存在！");
        }
    }

    public static <T> T getBean(String name, Class<T> requiredType)
            throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }


}