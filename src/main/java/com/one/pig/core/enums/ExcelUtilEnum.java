package com.one.pig.core.enums;


import com.anniu.ordercall.core.constant.CommonConstant;
import com.anniu.ordercall.core.utils.ExcelToolUtils;

public enum ExcelUtilEnum {

    export_07(CommonConstant.EXPROT07_TYPE, "", ".xlsx");

    private String type;

    private String fileType;

    private String url;

    private String suffix;

    private ExcelUtilEnum(String type, String fileType, String suffix) {
        this.type = type;
        this.fileType = fileType;
        this.suffix = suffix;
        this.url = ExcelToolUtils.appendUrl(fileType, type);
    }

    public static String getUrlByType(String type, String fileType) {
        for (ExcelUtilEnum result : ExcelUtilEnum.values()) {
            if (result.getType().equals(type) && result.getFileType().equals(fileType)) {
                return result.getUrl();
            }
        }
        return null;
    }

    public static String getSuffixByType(String type) {
        for (ExcelUtilEnum result : ExcelUtilEnum.values()) {
            if (result.getType().equals(type)) {
                return result.getSuffix();
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
