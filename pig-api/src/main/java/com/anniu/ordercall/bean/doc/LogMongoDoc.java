package com.anniu.ordercall.bean.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = LogMongoDoc.DOC_NAME)
@Data
public class LogMongoDoc implements Serializable {
    protected static final String DOC_NAME = "log";

    @Id
    private String id;

    /**
     * 日志名称
     */
    private String logName;
    /**
     * 管理员id
     */
    private Integer userId;
    /**
     *
     */
    private String userName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否执行成功
     */
    private String succeed;
    /**
     * 具体消息
     */
    private String message;
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String method;

}