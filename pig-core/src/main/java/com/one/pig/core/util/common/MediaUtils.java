package com.one.pig.core.util.common;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * 中位数工具类
 *
 * @author Looly
 */
public class MediaUtils {

    /**
     * 求中位数
     *
     * @param list 待测试类
     */
    //求中位数
    public static Double GetMedian(ArrayList<Double> list) {
        double middle = 0;
        int size = list.size();
        if (size != 0) {
            Double[] array = (Double[]) list.toArray(new Double[size]);
            Arrays.sort(array);
            if (size % 2 == 0) {
                middle = (array[size / 2 - 1] + array[size / 2]) / 2.0;
            } else {
                int inx = size / 2;
                middle = array[inx];
            }
        }
        return middle;
    }


}
