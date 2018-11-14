package com.one.pig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringBoot方式启动类
 *
 * @author csy
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(scanBasePackages = "com.one.pig")
public class PigApplication implements WebMvcConfigurer {

    protected final static Logger logger = LoggerFactory.getLogger(PigApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(PigApplication.class, args);
        logger.info("PigApplication is success!");
    }
}
