package com.one.pig.modular.system.controller;

import com.one.pig.common.annotion.Permission;
import com.one.pig.common.constant.Const;
import com.one.pig.common.controller.BaseController;
import com.one.pig.common.exception.BizExceptionEnum;
import com.one.pig.common.exception.BussinessException;
import com.one.pig.core.template.config.ContextConfig;
import com.one.pig.core.template.engine.SimpleTemplateEngine;
import com.one.pig.core.template.engine.base.PigTemplateEngine;
import com.one.pig.core.util.common.ToolUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @Date 2017-05-23 18:52:34
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private String PREFIX = "/system/code/";

    /**
     * 跳转到代码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "code.html";
    }

    /**
     * 代码生成
     */
    @ApiOperation("生成代码")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object add(@ApiParam(value = "模块名称",required = true) @RequestParam  String moduleName,
                      @RequestParam String bizChName,
                      @RequestParam String bizEnName,
                      @RequestParam String path) {
        if (ToolUtil.isOneEmpty(bizChName, bizEnName)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName(bizChName);
        contextConfig.setBizEnName(bizEnName);
        contextConfig.setModuleName(moduleName);
        if (ToolUtil.isNotEmpty(path)) {
            contextConfig.setProjectPath(path);
        }

        PigTemplateEngine pigTemplateEngine = new SimpleTemplateEngine();
        pigTemplateEngine.setContextConfig(contextConfig);
        pigTemplateEngine.start();

        return super.SUCCESS_TIP;
    }
}