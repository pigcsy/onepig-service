package com.anniu.ordercall.core.utils;

import com.anniu.ordercall.core.constant.CommonConstant;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelToolUtils {

    public static final Logger logger = Logger.getLogger(ExcelToolUtils.class);

    // 导出报表的服务器地址
    public static String appendUrl(String fileType, String excelType) {
        return new File(System.getProperty("user.dir")).getParent() + File.separator
                + "excel" + File.separator
                + fileType + File.separator
                + (excelType == CommonConstant.EXPROT07_TYPE ? excelType + "Up" : excelType)
                + File.separator;
    }

    // 报表流导出
    public static void exportMethod(Workbook work, String url, String filename) {
        FileOutputStream fileOut = null;
        try {
            File dirFile = new File(url);
            mkNoExistDir(dirFile);
            File file = new File(url + filename);
            file.createNewFile();
            fileOut = new FileOutputStream(file);
            work.write(fileOut);
            fileOut.flush();
        } catch (IOException e) {
            logger.error(e);
        } finally {
            try {
                work.close();
                fileOut.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    // 解决找不到文件夹的问题
    public static void mkNoExistDir(File dirFile) {
        if (!dirFile.exists()) {
            mkNoExistDir(dirFile.getParentFile());
            dirFile.mkdir();
        }
    }
}
