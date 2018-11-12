package com.one.pig.core.enums;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.anniu.ordercall.core.utils.FormatUtils.formatCurrency;


/**
 * Excel_格式化Enum
 */
public enum FormatEnum {

    FormatCurrency("FormatCurrency") {
        @Override
        public String formatParamter(String transAmt) {
            transAmt = transAmt == null ? "0" : transAmt;
            Long amt = Long.parseLong(transAmt);
            return String.valueOf(formatCurrency(amt));
        }
    }, TimestampToDateTime("TimestampToDateTime") {
        @Override
        public String formatParamter(String timstamp) {
            timstamp = timstamp == null ? "0" : timstamp;
            Long time = Long.parseLong(timstamp);
            Date date = new Date(time);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        }
    }, TimestampToDate("TimestampToDate") {
        @Override
        public String formatParamter(String timstamp) {
            timstamp = timstamp == null ? null : timstamp;
            return timstamp;
        }
    }, subCorpLiquidationList("subCorpLiquidationList") {
        @Override
        public String formatParamter(String param) {
            if (param == null) {
                return param;
            }
            try {
                JSONArray jsonArray = JSON.parseArray(param);
                if (jsonArray == null)
                    return "";
                String refund = "";
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    String corpName = json.getString("corpName");
                    corpName = corpName == null ? "" : (corpName + " ");
                    String date = json.getString("date");
                    date = date == null ? "" : (date + " ");
                    String acuralpay = json.getString("acturalPay");
                    acuralpay = acuralpay == null ? "" : ("缴存" + acuralpay + "(元) \r\n");
                    refund += date + corpName + acuralpay;
                }
                return refund;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    };
    private static Map<String, FormatEnum> enumMap;

    static {
        enumMap = new HashMap<>();
        for (FormatEnum formatEnum : FormatEnum.values()) {
            enumMap.put(formatEnum.getType(), formatEnum);
        }
    }

    private String type;

    FormatEnum(String type) {
        this.type = type;
    }

    public static FormatEnum getEnumByType(String type) {
        if (type == null)
            return null;
        return enumMap.get(type);
    }

    public String getType() {
        return type;
    }

    public abstract String formatParamter(String param);
}