package com.anniu.ordercall.controller.biz;


import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.core.base.BaseController;
import com.anniu.ordercall.service.biz.WorkbenchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


/**
 * 质量收入
 *
 * @author csy
 * 2017年1月10日 下午8:25:24
 */
@RestController
@RequestMapping(value = "/outbound-workbench", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WorkbenchController", description = "系统订单相关")
@Validated
@CrossOrigin
public class WorkbenchController extends BaseController {

    @Autowired
    private WorkbenchService workbenchService;

    /**
     * 客服点击执行的获取新单动作
     */
    @ApiOperation(value = "获取订单", notes = "获取订单按钮点击触发", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @ResponseBody
    @RequestMapping(value = "/get-order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity getOrder(UserDto userDto) {
        return ResponseEntity.success(workbenchService.getOrder(userDto.getUserId()));
    }

    /**
     * 客服点击执行的提交动作
     */
    @ApiOperation(value = "提交", notes = "提交订单按钮点击触发", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @ResponseBody
    @RequestMapping(value = "/submit-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity submitOrder(@NotBlank(message = "通话结果") @RequestParam String callResult, @NotBlank(message = "处理结果") @RequestParam String dealResult, @RequestParam Integer callOrderId) {
        workbenchService.submitOrder(callResult, dealResult, callOrderId);
        return ResponseEntity.success();
    }

    /**
     * 历史记录
     */
    @ApiOperation(value = "历史记录", notes = "历史记录", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "success")
    })
    @ResponseBody
    @RequestMapping(value = "/history-record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity historyRecord(UserDto userDto, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return ResponseEntity.success(workbenchService.historyRecord(pageNum, pageSize, userDto.getUserId()));
    }

    @ResponseBody
    @RequestMapping(value = "/vday-report", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity vdayReports(UserDto userDto) {
        return ResponseEntity.success(workbenchService.vDayReports(userDto.getUserId()));
    }
    
    @ResponseBody
    @RequestMapping(value = "/vday-record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity vdayRecord(UserDto userDto, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return ResponseEntity.success(workbenchService.vDayRecord(pageNum, pageSize, userDto.getUserId()));
    }
    
    @ResponseBody
    @RequestMapping(value = "/refund-ratio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity refundRatio(UserDto userDto) {
    	return ResponseEntity.success(workbenchService.refundRatio(userDto.getUserId()));
    }
    
    @ResponseBody
    @RequestMapping(value = "/searchOrderByPhone", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity searchOrderByPhone(String userPhone, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    	return ResponseEntity.success(workbenchService.searchOrderByPhone(pageNum,pageSize,userPhone));
    }
    
    @ResponseBody
    @RequestMapping(value = "/updatePayStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresUser
    public ResponseEntity updatePayStatus(@RequestParam String orderId,@RequestParam Integer payStatus) {
    	return ResponseEntity.success(workbenchService.updateEntity(orderId, payStatus));
    }
}
