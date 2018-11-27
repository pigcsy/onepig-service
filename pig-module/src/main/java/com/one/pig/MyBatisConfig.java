// package com.one.pig;
//
// import org.apache.ibatis.session.SqlSessionFactory;
// import org.mybatis.spring.SqlSessionFactoryBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
// import org.springframework.core.io.support.ResourcePatternResolver;
// import org.springframework.stereotype.Component;
//
// /**
//  * Created by kx on 17/4/2.
//  */
// @Component
// public class MyBatisConfig {
//     @Bean(name = "sqlSessionFactory")
//     public SqlSessionFactory sqlSessionFactoryBean() {
//     SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//  // bean.setDataSource(dataSource());
//
//     bean.setTypeAliasesPackage("com.one.pig");
//
//     // //分页插件设置
//     // PageHelper pageHelper = new PageHelper();
//     // Properties properties = new Properties();
//     // properties.setProperty("reasonable", "true");
//     // properties.setProperty("supportMethodsArguments", "true");
//     // properties.setProperty("returnPageInfo", "check");
//     // properties.setProperty("params", "count=countSql");
//     // pageHelper.setProperties(properties);
//     //
//     // //添加分页插件
//     // bean.setPlugins(new Interceptor[]{pageHelper});
//     //
//     ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//     try {
//         //基于注解扫描Mapper，不需配置xml路径
//         bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
//         return bean.getObject();
//     } catch (Exception e) {
//         e.printStackTrace();
//         throw new RuntimeException(e);
//     }
//     }
//
// }