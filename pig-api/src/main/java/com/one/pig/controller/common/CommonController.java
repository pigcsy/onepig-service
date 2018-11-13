package com.one.pig.controller.common;


import com.one.pig.common.controller.BaseController;
import com.one.pig.service.common.CommonService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author csy
 * @Date 2017年1月10日 下午8:25:24
 */
@RestController
@RequestMapping(value = "/common", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "CommonController", description = "通用")
@Validated
@CrossOrigin
public class CommonController extends BaseController {

    @Autowired
    CommonService commonService;


}
