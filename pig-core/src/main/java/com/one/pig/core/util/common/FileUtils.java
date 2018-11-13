package com.one.pig.core.util.common;


import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class FileUtils {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(FileUtils.class);

    // /**
    //  * NIO way
    //  */
    // public static byte[] toByteArray(String filename) {
    //
    //     File f = new File(filename);
    //     if (!f.exists()) {
    //         log.error("文件未找到！" + filename);
    //         throw new ApiException(FILE_NOT_FOUND.getCode(), FILE_NOT_FOUND.getMessage());
    //     }
    //     FileChannel channel = null;
    //     FileInputStream fs = null;
    //     try {
    //         fs = new FileInputStream(f);
    //         channel = fs.getChannel();
    //         ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
    //         while ((channel.read(byteBuffer)) > 0) {
    //             // do nothing
    //             // System.out.println("reading");
    //         }
    //         return byteBuffer.array();
    //     } catch (IOException e) {
    //         throw new ApiException(FILE_READING_ERROR.getCode(), FILE_READING_ERROR.getMessage());
    //     } finally {
    //         try {
    //             channel.close();
    //         } catch (IOException e) {
    //             throw new ApiException(FILE_READING_ERROR.getCode(), FILE_READING_ERROR.getMessage());
    //         }
    //         try {
    //             fs.close();
    //         } catch (IOException e) {
    //             throw new ApiException(FILE_READING_ERROR.getCode(), FILE_READING_ERROR.getMessage());
    //         }
    //     }
    // }

    /**
     * 文件下载根据浏览器转化输出编码
     *
     * @param request
     * @param realFileName
     * @return
     * @throws Exception
     */
    public static String getFilenameStr(HttpServletRequest request,
                                        String realFileName) throws Exception {
        String browName = null;
        String clientInfo = request.getHeader("User-agent");
        System.out.println(clientInfo);
        if (clientInfo != null && clientInfo.indexOf("MSIE") > 0) {//
            // IE采用URLEncoder方式处理
            if (clientInfo.indexOf("MSIE 6") > 0
                    || clientInfo.indexOf("MSIE 5") > 0) {// IE6，用GBK，此处实现有局限性
                browName = new String(realFileName.getBytes("UTF-8"),
                        "ISO-8859-1");
            } else {// ie7+用URLEncoder方式
                browName = java.net.URLEncoder.encode(realFileName, "UTF-8");
            }
        } else {// 其他浏览器
            browName = new String(realFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        return browName;
    }
}