package com.one.pig.core.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.net.URLEncoder;

public class ZsMessageUtil {

    //普通短信
    private void sendsms(String userPhone) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.1cloudsp.com/api/v2/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String accesskey = "eo7Oi7ybHY4XCamd"; //用户开发key
        String accessSecret = "bmuCcGDmKJA0gGHQNRyHarJv6kdMvPb2"; //用户开发秘钥

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "123"),
                new NameValuePair("templateId", "100"),
                new NameValuePair("mobile", userPhone),
                new NameValuePair("content", URLEncoder.encode("ceshi", "utf-8"))//（示例模板：{1}您好，您的订单于{2}已通过{3}发货，运单号{4}）
        };
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("Connection", "close");

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
    }

    // public static void main(String[] args) throws Exception {
    //     ZsMessageUtil t = new ZsMessageUtil();
    //     //普通短信
    //     t.sendsms();
    // }
}