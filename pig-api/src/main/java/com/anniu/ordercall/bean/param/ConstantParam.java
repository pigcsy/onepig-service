package com.anniu.ordercall.bean.param;

import java.util.Date;

/**
 * 常量参数
 * @ClassName: ConstantParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class ConstantParam {

    /*####################################常量(Start)#########################################*/

    /**
     * 常量/常数
     * -- 0.00
     */
    public static final Double ConstantZeroPointZeroZero = 0.00;

    /**
     * 常量/常数
     * -- -2
     */
    public static final Integer ConstantNegTwo = -2;

    /**
     * 常量/常数
     * -- -1
     */
    public static final Integer ConstantNegOne = -1;

    /**
     * 常量/常数
     * -- 0
     */
    public static final Integer ConstantZero = 0;

    /**
     * 常量/常数
     * -- 1
     */
    public static final Integer ConstantOne = 1;

    /**
     * 常量/常数
     * -- 2
     */
    public static final Integer ConstantTwo = 2;

    /**
     * 常量/常数
     * -- 3
     */
    public static final Integer ConstantThree = 3;

    /**
     * 常量/常数
     * -- 4
     */
    public static final Integer ConstantFour = 4;

    /**
     * 常量/常数
     * -- 5
     */
    public static final Integer ConstantFives = 5;

    /**
     * 常量/常数
     * -- 6
     */
    public static final Integer ConstantSix = 6;

    /**
     * 常量/常数
     * -- 7
     */
    public static final Integer ConstantSeven = 7;

    /**
     * 常量/常数
     * -- 8
     */
    public static final Integer ConstantEight = 8;

    /**
     * 常量/常数
     * -- 9
     */
    public static final Integer ConstantNine = 9;

    /**
     * 常量/常数
     * -- 10
     */
    public static final Integer ConstantTen = 10;

    /**
     * 常量/常数
     * -- 20
     */
    public static final Integer ConstantTwenty = 20;

    /**
     * 常量/常数
     * -- 30
     */
    public static final Integer ConstantThirty = 30;

    /**
     * 常量/常数
     * -- 50
     */
    public static final Integer ConstantFifty = 50;

    /**
     * 常量/常数
     * -- 60
     */
    public static final Integer ConstantSixty = 60;

    /**
     * 常量/常数
     * -- 80
     */
    public static final Integer ConstantEighty = 80;

    /**
     * 常量/常数
     * -- 100
     */
    public static final Integer ConstantHundred = 100;

    /**
     * 常量
     * -- true
     */
    public static final boolean CurrentTrue = true;

    /**
     * 常量
     * -- false
     */
    public static final boolean CurrentFalse = false;

    /**
     * 排序方式
     * -- desc 降序
     */
    public static final String SortingMannerDesc = "desc";

    /**
     * 排序方式
     * -- asc 升序
     */
    public static final String SortingMannerAsc = "asc";

    /**
     * 状态
     * -- 下架
     */
    public static final String StatusUnderShelf = "UnderShelf";

    /**
     * 状态
     * -- 上架
     */
    public static final String StatusUpperShelf = "UpperShelf";

    /**
     * 客服 Key
     * -- Redis Key
     */
    public static final String CustomerRedisKey = "Customer.RealTime";

    /**
     * 管理 Key
     * -- Redis Key
     */
    public static final String ManageRedisKey = "Manage.RealTime";

    /**
     * 过期时间
     * -- Redis 过期时间
     */
    public static final Integer RedisExpiredTime = 3600;

    /*####################################常量(end)###########################################*/


	/**
	 * 获取当前时间
	 * @return 处理结果
	 */
	public static Date obtainCurrentDateTime() {
		return new Date();
	}

    /**
     * 私有化构造器
     * -- 禁止初始化实例, 建议使用类名.方法使用
     */
    private ConstantParam() {

    }

}
