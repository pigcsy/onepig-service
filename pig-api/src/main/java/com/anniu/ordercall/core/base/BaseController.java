package com.anniu.ordercall.core.base;

import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.core.utils.FileUtils;
import com.anniu.ordercall.core.utils.HttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.INPUT_ERR;

public class BaseController {

    protected static String SUCCESS = "SUCCESS";
    protected static String ERROR = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected HttpServletRequest getHttpServletRequest() {
        return HttpUtils.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpUtils.getResponse();
    }

    protected HttpSession getSession() {
        return HttpUtils.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpUtils.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpUtils.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpUtils.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }


    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 返回前台文件流
     *
     * @author csy
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {
        byte[] bytes = FileUtils.toByteArray(filePath);
        return renderFile(fileName, bytes);
    }

    /**
     * 返回前台文件流
     *
     * @author csy
     * @date 2017年2月28日 下午2:53:19
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }

    public void validate(BindingResult result) {
        if (result.hasFieldErrors()) {
            throw new ApiException(500, result.getFieldErrors().get(0).getDefaultMessage());
        }
        if (result.hasErrors()) {
            throw new ApiException(INPUT_ERR.getCode(), INPUT_ERR.getMessage());
        }
    }
}
