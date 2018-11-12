package com.one.pig.core.util.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FormatUtils {
    /**
     * 格式化货比
     *
     * @param currency
     * @return
     */
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

    /**
     * 格式化double类型货币
     *
     * @param currency
     * @return
     */
    public static double formatDoubleCurrency(Double currency) {
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

    /**
     * 格式化金额,分-元
     *
     * @param amount
     * @return
     */
    public static Object formatCurBackDouble(Object amount) {
        BigDecimal currencyBig = (BigDecimal) amount;
        BigDecimal changeBig = new BigDecimal("100");
        MathContext format = new MathContext(currencyBig.precision(),
                RoundingMode.HALF_UP);

        Double result = currencyBig.divide(changeBig, format).doubleValue();

        return result;
    }


}
