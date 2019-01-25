package com.one.pig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * SpringBoot方式启动类
 *
 * @author csy
 * @Date 2017/5/21 12:06
 */
@EnableSwagger2
@EnableScheduling
@Configuration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages ={"com.one.pig"})
@MapperScan(basePackages = "com.one.pig.system")
public class PigApplication extends SpringBootServletInitializer {

    protected final static Logger logger = LoggerFactory.getLogger(PigApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PigApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PigApplication.class);
    }
}
