package com.anniu.ordercall.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

enum FormatEnumUtils {
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
    private static Map<String, FormatEnumUtils> enumMap;

    static {
        enumMap = new HashMap<>();
        for (FormatEnumUtils formatEnumUtils : FormatEnumUtils.values()) {
            enumMap.put(formatEnumUtils.getType(), formatEnumUtils);
        }
    }

    private String type;

    FormatEnumUtils(String type) {
        this.type = type;
    }

    public static FormatEnumUtils getEnumByType(String type) {
        if (type == null)
            return null;
        return enumMap.get(type);
    }

    /**
     * 格式化货比
     *
     * @param currency
     * @return
     */
    // public static String formatCurrency(Long currency) {
    // BigDecimal currencyBig = new BigDecimal(currency);
    // BigDecimal changeBig = new BigDecimal("100");
    // MathContext format = new MathContext(currencyBig.precision(),
    // RoundingMode.HALF_UP);
    //
    // BigDecimal resultBig = currencyBig.divide(changeBig, format);
    // String result = resultBig.toString();
    //
    // return result;
    // }
    public static double formatCurrency(Long currency) {
        if (currency == null) {
            return 0;
        }
        BigDecimal currencyBig = new BigDecimal(currency);
        BigDecimal changeBig = new BigDecimal("100");
        MathContext format = new MathContext(currencyBig.precision(),
                RoundingMode.HALF_UP);

        BigDecimal resultBig = currencyBig.divide(changeBig, format);
        double result = resultBig.doubleValue();

        return result;
    }

    public String getType() {
        return type;
    }

    public abstract String formatParamter(String param);
}