package com.one.pig.core.constant;


import java.util.Calendar;

public class CommonConstant {


    /***Freemarker 使用的变量 begin**/

    public final static String TARGET = "target";//标签使用目标
    public static final String OUT_TAG_NAME = "outTagName";//输出标签Name
    /**
     * Integer
     */
    public static final Integer I_ZERO = 0;
    public static final Integer I_ONE = 1;
    public static final Integer I_TOW = 2;
    public static final Integer I_THREE = 3;
    /**
     * double
     */
    public static final double D_ZERO = 0.0;
    public static final double D_ONE = 1.0;
    public static final double D_TOW = 2.0;
    public static final double D_THREE = 3.0;
    /**
     * byte
     */
    public static final byte B_ZERO = 0;
    public static final byte B_ONE = 1;
    public static final byte B_TOW = 2;
    public static final byte B_THREE = 3;
    public static final int TIME_OUT_SECOND = 86400 * 3;
    static final String CONTEXT_PATH = "contextPath";
    /**
     * 其他常用变量 begin
     **/
    static final String NAME = "name";
    static final String ID = "userId";
    static final String TOKEN = "token";
    /***项目根路径*/
    static final String LOING_USER = "loing_user";
    /**
     * Long
     */
    static final Long ZERO = new Long(0);
    /***Freemarker 使用的变量 end**/
    static final Long ONE = new Long(1);
    static final Long TWO = new Long(2);
    static final Long THREE = new Long(3);
    static final Long EIGHT = new Long(8);
    /**
     * String
     */
    static final String S_ZERO = "0";
    static final String S_ONE = "1";
    static final String S_TOW = "2";
    static final String S_THREE = "3";
    /**
     * cache常用变量 begin
     **/
    static final String CACHE_NAME = "shiro_cache";
    static final String CACHE_MANAGER = "cacheManager";//cacheManager bean name
    /**
     * 当前年份
     **/
    static final int NOW_YEAY = Calendar.getInstance().get(Calendar.YEAR);
    /**
     * 其他常用变量 end
     **/
    //存储到缓存，标识用户的禁止状态，解决在线用户踢出的问题
    final static String EXECUTE_CHANGE_USER = "ANNIU_EXECUTE_CHANGE_USER";
    // job同步锁
    public static byte[] JOB_LOCK = new byte[1024];
    /**
     * cache常用变量 end
     **/
    /*
     * redis 服务配置信息
     */
    public static String REDIS_IP = "redis.server.ip";
    public static String REDIS_PWD = "redis.server.password";
    public static String XYZC_ORDER_INFO = "REDIS.ORDER.INFO";
    // 导出报表类型
    public static String EXPROT07_TYPE = "07";
    public static String EXPROT03_TYPE = "03";
    static String VERSION = String.valueOf(System.currentTimeMillis());//版本号，重启的时间

    public static class SysResourceType {
        public static String RESOURCE_TYPE_STR = "resourceType";
        public static int CONTENTS_TYPE = 1;
        public static int MENU_TYPE = 2;
        public static int HOSPITAL_TYPE = 3;
        public static int UNION_TYPE = 4;
    }

    public static class PermissionSuffix {
        public static String MEIDCAL_SUFFIX = "medical:";
        public static String UNION_SUFFIX = "union:";
    }

}
