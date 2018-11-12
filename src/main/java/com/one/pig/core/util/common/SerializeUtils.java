package com.one.pig.core.util.common;


import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 开发公司：anniu在线工具 <p>
 * 版权所有：© www.anniu.com<p>
 * 博客地址：http://www.anniu.com/blog/  <p>
 * <p>
 * <p>
 * Java原生版的 Serialize
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　csy　2016年6月2日 　<br/>
 *
 * @author csy
 * @version 1.0, 2016年6月2日 <br/>
 * @email so@anniu.com
 */
@SuppressWarnings("unchecked")
public class SerializeUtils {
    static final Class<?> CLAZZ = SerializeUtils.class;

    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
            LoggerUtils.fmtError(CLAZZ, e, "serialize error %s", JSONObject.toJSON(value));
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }


    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    public static <T> T deserialize(byte[] in, Class<T>... requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            com.anniu.ordercall.core.utils.LoggerUtils.fmtError(CLAZZ, e, "serialize error %s", in);
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }

    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                LoggerUtils.fmtError(CLAZZ, "close stream error");
            }
    }

}
