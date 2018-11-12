package com.one.pig.core.util.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.core.enums.FormatEnum;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.anniu.ordercall.core.constant.CommonConstant.I_ONE;
import static com.anniu.ordercall.core.constant.CommonConstant.I_ZERO;
import static com.anniu.ordercall.core.exception.BizExceptionEnum.*;

/**
 * Created by zilue on 2017/1/16.
 * <p/>
 * 支持json格式的动态报表输出
 * 头信息mappingJson（JsonArray）
 * 内容dataJson（JsonArray）
 * <p/>
 * mappingJson结构如下（列顺序与key-value顺序一致）
 * [{"cloumKey":"loginName","cloumName":"用户名"},{"cloumKey":"createDate","cloumName":"创建时间"}]
 * <p/>
 * dataJson结构
 * collection转化成的JsonArray
 */

public class ExcelUtil {

    public static final String CLOUM_TYPE = "type";
    public static final String CLOUM_KEY = "property";
    public static final String CLOUM_NAME = "name";
    public static final String CLOUM_FORMAT = "format";

    public static final int TYPE_STRING = I_ONE;
    public static final int TYPE_NUMBER = I_ZERO;
    ;

    /**
     * 生成多个sheet的报表
     *
     * @param execlType
     * @param headerMappingJsonList
     * @param dataListMap
     * @param outputStream
     * @param <T>
     */
    public static <T> void createReportMoreSheets(String execlType, List<String> headerMappingJsonList, LinkedHashMap<String, List<T>> dataListMap, OutputStream outputStream) {
        ExeclEnum execlTypeEnum;
        if (ExeclEnum.EXECL_2003.getType().equals(execlType))
            execlTypeEnum = ExeclEnum.EXECL_2003;
        else
            execlTypeEnum = ExeclEnum.EXECL_2007;

        if (headerMappingJsonList != null && dataListMap != null && headerMappingJsonList.size() == dataListMap.size()) {
            throw new ApiException(STITCHING_EXCEL_ERROR.getCode(), STITCHING_EXCEL_ERROR.getMessage());
        }
        Workbook workBook = ExcelUtil.getWorkBook(execlTypeEnum);
        Iterator<Map.Entry<String, List<T>>> iterator = dataListMap.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            String headerMappingJson = headerMappingJsonList.get(index);
            JSONArray mappingJson = JSON.parseArray(headerMappingJson);
            Map.Entry<String, List<T>> nextSheet = iterator.next();
            String sheetName = nextSheet.getKey();
            List<T> dataValue = nextSheet.getValue();
            JSONArray dataJson = JSON.parseArray(JSON.toJSONString(dataValue));
            ExcelUtil.creatSheet(workBook, mappingJson, dataJson, sheetName, 1);
            index++;
        }
        ExcelUtil.writeToOutputStream(workBook, outputStream);
    }

    /**
     * 生成单个sheet的报表
     *
     * @param execlType
     * @param headerMappingJson
     * @param sheetName
     * @param dataList
     * @param outputStream
     * @param <T>
     */
    public static <T> void createReportOneSheet(String execlType, String headerMappingJson, String sheetName, List<T> dataList, OutputStream outputStream) {
        ExeclEnum execlTypeEnum;
        if (ExeclEnum.EXECL_2003.getType().equals(execlType))
            execlTypeEnum = ExeclEnum.EXECL_2003;
        else
            execlTypeEnum = ExeclEnum.EXECL_2007;
        JSONArray mappingJson = JSON.parseArray(headerMappingJson);
        Workbook workBook = ExcelUtil.getWorkBook(execlTypeEnum);
        JSONArray dataJson = JSON.parseArray(JSON.toJSONString(dataList));
        ExcelUtil.creatSheet(workBook, mappingJson, dataJson, sheetName, 1);
        ExcelUtil.writeToOutputStream(workBook, outputStream);
    }

    /**
     * 创建报表workbook
     *
     * @param execlType
     * @return
     */
    public static Workbook getWorkBook(ExeclEnum execlType) {
        if (ExeclEnum.EXECL_2007.equals(execlType)) {
            return new XSSFWorkbook();
        } else if (ExeclEnum.EXECL_2003.equals(execlType)) {
            return new HSSFWorkbook();
        } else {
            return new XSSFWorkbook();
        }
    }

    /**
     * 输出报表到文件
     *
     * @param workbook
     * @param outputStream
     */
    public static void writeToOutputStream(Workbook workbook, OutputStream outputStream) {
        try {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new ApiException(STREAM_EXCEL_ERROR.getCode(), STREAM_EXCEL_ERROR.getMessage());
        }
    }

    /**
     * 创建sheet
     *
     * @param workbook
     * @param mappingJson mappingJson结构（列顺序为先后顺序） [{"cloumKey":"loginName","cloumName":"用户名"},{"cloumKey":"createDate","cloumName":"创建时间"}]
     * @param dataJson
     * @param sheetName
     * @param sheetIndex
     */
    public static void creatSheet(Workbook workbook, JSONArray mappingJson, JSONArray dataJson, String sheetName, Integer sheetIndex) {
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.autoSizeColumn(sheetIndex, true);
        List<Integer> cloumLengths = new ArrayList<>();
        creatTableHead(workbook, sheet, mappingJson, cloumLengths);
        try {
            creatTableBody(workbook, sheet, mappingJson, dataJson, cloumLengths);
        } catch (Exception e) {
            throw new ApiException(EXCEL_ERROR.getCode(), EXCEL_ERROR.getMessage());
        }
    }

    /**
     * 设置表头
     *
     * @param work
     * @param sheet
     * @param mappingJson
     * @param lengthList
     */
    private static void creatTableHead(Workbook work, Sheet sheet, JSONArray mappingJson, List<Integer> lengthList) {
        CellStyle hstyle = work.createCellStyle();
        hstyle.setAlignment(HorizontalAlignment.GENERAL);
        hstyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
        if (mappingJson == null) {
            throw new ApiException(EXCEL_HEADER_NULL.getCode(), EXCEL_HEADER_NULL.getMessage());
        }
        int row = 0;
        Row hRow = sheet.createRow(row++);
        for (int index = 0; index < mappingJson.size(); index++) {
            JSONObject jsonObject = mappingJson.getJSONObject(index);
            String cloumName = jsonObject.getString(CLOUM_NAME);
            cloumName = cloumName == null ? "无列头(warning)" : cloumName;
            Cell cell = hRow.createCell(index);
            cell.setCellStyle(hstyle);
            cell.setCellValue(cloumName);
            sheet.setColumnWidth(index, cloumName.getBytes().length * 256);
            lengthList.add(cloumName.getBytes().length);
        }
    }

    /**
     * 设置表体
     *
     * @param work
     * @param sheet
     * @param mappingJson
     * @param dataJson
     * @param lengthList
     */
    private static void creatTableBody(Workbook work, Sheet sheet, JSONArray mappingJson, JSONArray dataJson, List<Integer> lengthList) {
        CellStyle hstyle = work.createCellStyle();
        hstyle.setWrapText(true);
        hstyle.setAlignment(HorizontalAlignment.GENERAL);
        hstyle.setVerticalAlignment(VerticalAlignment.JUSTIFY);
        int row = sheet.getLastRowNum();
        try {
            for (int i = 0; i < dataJson.size(); i++) {
                JSONObject data = dataJson.getJSONObject(i);
                Row hRow = sheet.createRow(++row);
                for (int index = 0; index < mappingJson.size(); index++) {
                    JSONObject jsonObject = mappingJson.getJSONObject(index);
                    String formateType = jsonObject.getString(CLOUM_FORMAT);
                    FormatEnum formatEnum = FormatEnum.getEnumByType(formateType);
                    String clomkey = jsonObject.getString(CLOUM_KEY);
                    Integer clomstype = jsonObject.getInteger(CLOUM_TYPE);

                    String value = data.getString(clomkey);
                    if (formatEnum != null)
                        value = formatEnum.formatParamter(value);
                    Cell cell = hRow.createCell(index, CellType.NUMERIC);//
                    cell.setCellStyle(hstyle);

                    if (clomstype != null) {
                        if (clomstype.intValue() == TYPE_NUMBER)
                            cell.setCellValue(Double.valueOf(value == null ? "0" : value));
                        else {
                            cell.setCellValue(value);
                        }
                    } else
                        cell.setCellValue(value);

                    if (value != null && value.getBytes().length > lengthList.get(index)) {

                        String[] split = value.split("\r");
                        int length = 0;
                        for (String s : split) {
                            length = s.getBytes().length > length ? s.getBytes().length : length;
                        }
                        lengthList.set(index, length);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApiException(EXCEL_DATA_NULL.getCode(), EXCEL_DATA_NULL.getMessage());
        }
        for (int j = 0; j < lengthList.size(); j++) {
            sheet.setColumnWidth(j, lengthList.get(j) * 256);
        }
    }


    /**
     * Excel枚举类
     */
    public enum ExeclEnum {
        EXECL_2007("07", "xlsx"), EXECL_2003("03", "xls");
        private String type;
        private String suffix;

        ExeclEnum(String type, String suffix) {
            this.type = type;
            this.suffix = suffix;
        }

        public String getType() {
            return type;
        }

        public String getSuffix() {
            return suffix;
        }

        public String getFilteFullName(String name) {
            return name + "." + suffix;
        }
    }


}

