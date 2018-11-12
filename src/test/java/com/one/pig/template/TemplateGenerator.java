package com.one.pig.template;

import com.one.pig.core.template.config.ContextConfig;
import com.one.pig.core.template.engine.SimpleTemplateEngine;
import com.one.pig.core.template.engine.base.PigTemplateEngine;

import java.io.IOException;

/**
 * 测试pig模板引擎
 *
 * @author fengshuonan
 * @date 2017-05-09 20:27
 */
public class TemplateGenerator {

    public static void main(String[] args) throws IOException {
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName("啊哈");
        contextConfig.setBizEnName("haha");
        contextConfig.setModuleName("tk");
        contextConfig.setProjectPath("D:\\tmp\\pig");

        //contextConfig.setAddPageSwitch(false);
        //contextConfig.setEditPageSwitch(false);

        PigTemplateEngine pigTemplateEngine = new SimpleTemplateEngine();
        pigTemplateEngine.setContextConfig(contextConfig);
        pigTemplateEngine.start();
    }

}
