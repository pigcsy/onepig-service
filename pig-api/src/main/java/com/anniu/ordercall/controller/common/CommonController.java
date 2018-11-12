package com.anniu.ordercall.controller.common;


import com.anniu.ordercall.core.base.BaseController;
import com.anniu.ordercall.service.common.CommonService;
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
