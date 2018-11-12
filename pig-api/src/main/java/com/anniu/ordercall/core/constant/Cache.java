package com.anniu.ordercall.core.constant;

import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.READIS_ERROR;

/**
 * 所有缓存名称的集合
 *
 * @author csy
 * @date 2017-04-24 21:56
 */
public interface Cache {

    /**
     * 常量缓存
     */
    String CONSTANT = "CONSTANT";

    /**
     * 验证码在redis中的key和过期时间
     */
    // public static final RedisConst SUBCOMB_EMAIL_CODE = new RedisConst("FEYONG_EMIL_CODE_{}_TYPE_{}", 900);
    //
    // public static final RedisConst SUBCOMB_HOUR_DATA_CODE = new RedisConst("FEYONG_HOUR_DATA_CODE_{}_TYPE_{}", 604800);
    //
    // public static final RedisConst SUBCOMB_DATE_DATA_CODE = new RedisConst("FEYONG_HOUR_DATA_CODE_{}_TYPE_{}");


    public static final String SUBCOMB_EMAIL_CODE = "FEYONG_EMIL_CODE_{}_TYPE_{}";

    public static final String SUBCOMB_HOUR_DATA_CODE = "FEYONG_HOUR_CODE_{}_TYPE_{}";

    public static final String SUBCOMB_DATE_DATA_CODE = "FEYONG_DATA_CODE_{}_TYPE_{}";


    /**
     * Created by 轴承 on 16/6/28 下午1:59.
     */
    public static class RedisConst implements Serializable {
        private static final long serialVersionUID = -788058748951890721L;
        /**
         * 缓存中的key
         */
        private String key;
        /**
         * 缓存中key对应的过期时间
         */
        private int expire;

        /**
         * hashmap中key的field
         */
        private String field;

        public RedisConst() {
        }

        public RedisConst(String key, int expire) {
            this.key = key;
            this.expire = expire;
        }

        public RedisConst(String key, String field) {
            this.key = key;
            this.field = field;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getRedisKey(String... replaceStr) {
            return replaceConstStr(getKey(), replaceStr);
        }

        public String getRedisField(String... replaceStr) {
            return replaceConstStr(getField(), replaceStr);
        }

        public String getFuzzyRedisKey(String... replaceStr) {
            String result = getKey();
            int index = 0;
            while (result.indexOf("{}") >= 0) {
                if (index >= replaceStr.length) {
                    result = StringUtils.replaceOnce(result, "{}", "*");
                    index++;
                    continue;
                }
                result = StringUtils.replaceOnce(result, "{}", replaceStr[index]);
                index++;
            }
            return result;
        }

        private String replaceConstStr(String string, String... replaceStr) {
            if (string == null)
                return null;
            String result = string;
            int index = 0;
            while (result.indexOf("{}") >= 0) {
                if (ArrayUtils.isEmpty(replaceStr) || replaceStr.length < index) {
                    throw new ApiException(READIS_ERROR.getCode(), READIS_ERROR.getMessage());
                }
                result = StringUtils.replaceOnce(result, "{}", replaceStr[index]);
                index++;
            }
            return result;
        }
    }

}
