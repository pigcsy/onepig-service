package com.one.pig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Created by kx on 17/4/2.
 */
@Configuration
//必须在MyBatisConfig注册后再加载MapperScannerConfigurer，否则会报错
// @AutoConfigureAfter(MyBatisConfig.class)
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.one.pig");

        // //初始化扫描器的相关配置，这里我们要创建一个Mapper的父类
        // Properties properties = new Properties();
        // properties.setProperty("mappers", "com.kx.springboot.dao.baseDao.MyMapper");
        // properties.setProperty("notEmpty", "false");
        // properties.setProperty("IDENTITY", "MYSQL");
        //
        // mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }
}