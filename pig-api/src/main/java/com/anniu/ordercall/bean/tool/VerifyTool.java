package com.anniu.ordercall.bean.tool;

import com.anniu.ordercall.bean.annotation.Column;
import com.anniu.ordercall.bean.annotation.Sorting;
import com.anniu.ordercall.bean.enums.ResultEnum;
import com.anniu.ordercall.bean.param.ConstantParam;
import com.anniu.ordercall.bean.param.PagingParam;
import com.anniu.ordercall.bean.param.SortingParam;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 校验Tool
 * @ClassName: VerifyTool
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class VerifyTool {

    /**
     * 校验属性是否存在
     * @param classes Class类
     * @param fieldName 字段名
     * @return 校验结果
     */
    public static Boolean verifyPropertyExist(Class classes, String fieldName) {
        /**
         * 定义boolean变量
         */
        boolean exist = false;
        /**
         * 通过Class的getDeclaredFields(反射机制)获取Field对象信息
         */
        Field[] declaredFields = classes.getDeclaredFields();
        for (Field itemField : declaredFields) {
            if (itemField.getName().equals(fieldName)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    /**
     * 校验排序属性
     * @param classes Class类
     * @param fieldName 字段名
     * @return 校验结果
     */
    public static Boolean verifySortingProperty(Class classes, String fieldName) {
        /**
         * 定义boolean变量
         */
        boolean exist = false;
        /**
         * 通过Class的getDeclaredFields(反射机制)获取Field对象信息
         */
        Field[] declaredFields = classes.getDeclaredFields();
        for (Field itemField : declaredFields) {
            /**
             * 私有属性需要设置访问权限
             */
            itemField.setAccessible(ConstantParam.CurrentTrue);
            if (fieldName.equals("createTime") || fieldName.equals("modifyTime")) {
                exist = true;
                break;
            } else {
                /**
                 * 判断属性是否存在
                 */
                if (itemField.getName().equals(fieldName)) {
                    /**
                     * 判断是否注解
                     */
                    boolean hasAnnotation = itemField.isAnnotationPresent(Sorting.class);
                    if (hasAnnotation) exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    /**
     * 校验参数
     * @param data 参数对象
     * @return 校验结果
     */
    public static void verifyParam(Object data) {
        /**
         * 通过Class的getDeclaredFields(反射机制)获取Field对象信息
         */
        Field[] declaredFields = data.getClass().getDeclaredFields();
        for (Field itemField : declaredFields) {
            /**
             * 私有属性需要设置访问权限
             */
            itemField.setAccessible(ConstantParam.CurrentTrue);
            /**
             * 判断是否注解
             */
            boolean hasAnnotation = itemField.isAnnotationPresent(Column.class);
            if (hasAnnotation) {
                Column column = itemField.getAnnotation(Column.class);
                try {
                    Object itemValue = itemField.get(data);
                    if (itemValue == null) {
                        throw new OpenException(column.msg(), ResultEnum.ClientError);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new OpenException(column.msg(), ResultEnum.ClientError);
                }
            }
        }
    }

    /**
     * 校验排序参数
     * @param sortingParam 参数对象
     * @param verifyClass 校验参数 Class
     * @param sorting 默认排序
     * @return 处理结果
     */
    public static List<SortingParam> verifySortingParamTo(List<SortingParam> sortingParam, Class verifyClass, SortingParam sorting) {
        if (sortingParam != null && !sortingParam.isEmpty() && sortingParam.size() > ConstantParam.ConstantZero) {
            List removes = new ArrayList();
            for (SortingParam item : sortingParam) {
                if (!verifySortingProperty(verifyClass, item.getPropertyName())) removes.add(item);
                if (item.getSortingManner() == null || (!item.getSortingManner().equals(ConstantParam.SortingMannerAsc) && !item.getSortingManner().equals(ConstantParam.SortingMannerDesc)))
                    item.setSortingManner(ConstantParam.SortingMannerDesc);
            }
            sortingParam.removeAll(removes);
            for (int i = 0; i < sortingParam.size() - 1; i++) {
                for (int j = sortingParam.size() - 1; j > i; j--) {
                    if (sortingParam.get(j).getPropertyName().equals(sortingParam.get(i).getPropertyName()))
                        sortingParam.remove(j);
                }
            }
        }
        if (sortingParam == null || sortingParam.isEmpty() || sortingParam.size() <= ConstantParam.ConstantZero) {
            sortingParam = new ArrayList<>();
            sortingParam.add(sorting);
        }
        return sortingParam;
    }

    /**
     * 校验排序参数
     * @param sortingParam 参数对象
     * @param verifyClass 校验参数 Class
     * @param sorting 默认排序
     * @return 处理结果
     */
    public static List<SortingParam> verifySortingParam(List<SortingParam> sortingParam, Class verifyClass, SortingParam sorting) {
        if (sortingParam != null && !sortingParam.isEmpty() && sortingParam.size() > ConstantParam.ConstantZero) {
            List removes = new ArrayList();
            for (SortingParam item : sortingParam) {
                if (item.getPropertyName() == null || !verifySortingProperty(verifyClass, item.getPropertyName()))
                    removes.add(item);
                if (item.getSortingManner() == null || (!item.getSortingManner().equals(ConstantParam.SortingMannerAsc) && !item.getSortingManner().equals(ConstantParam.SortingMannerDesc)))
                    item.setSortingManner(ConstantParam.SortingMannerDesc);
            }
            sortingParam.removeAll(removes);
            for (int i = 0; i < sortingParam.size() - 1; i++) {
                for (int j = sortingParam.size() - 1; j > i; j--) {
                    if (sortingParam.get(j).getPropertyName().equals(sortingParam.get(i).getPropertyName()))
                        sortingParam.remove(j);
                }
            }
        }
        if (sortingParam == null || sortingParam.isEmpty() || sortingParam.size() <= ConstantParam.ConstantZero) {
            sortingParam = new ArrayList<>();
            sortingParam.add(sorting);
        }
        return sortingParam;
    }

    /**
     * 校验分页参数
     * @param pagingParam 参数对象
     * @param verifyClass 校验参数 Class
     * @param sorting 默认排序
     * @return
     */
    public static void verifyPagingParam(PagingParam pagingParam, Class verifyClass, SortingParam sorting) {
        if (pagingParam.getPage() == null || pagingParam.getPage() <= ConstantParam.ConstantZero)
            pagingParam.setPage(ConstantParam.ConstantOne);
        if (pagingParam.getSize() == null || pagingParam.getSize() <= ConstantParam.ConstantZero)
            pagingParam.setSize(ConstantParam.ConstantTen);
        /**
         * 校验排序参数
         */
        pagingParam.setSortingParam(verifySortingParamTo(pagingParam.getSortingParam(), verifyClass, sorting));
    }

    /**
     * 校验分页参数
     * @param pagingParam 参数对象
     * @param verifyClass 校验参数 Class
     * @param sorting 默认排序
     * @param isSetDefault 是否设置默认
     * @return
     */
    public static void verifyPagingParam(PagingParam pagingParam, Class verifyClass, SortingParam sorting, Boolean isSetDefault) {
        if (isSetDefault) {
            if (pagingParam.getPage() == null || pagingParam.getPage() <= ConstantParam.ConstantZero)
                pagingParam.setPage(ConstantParam.ConstantOne);
            if (pagingParam.getSize() == null || pagingParam.getSize() <= ConstantParam.ConstantZero)
                pagingParam.setSize(ConstantParam.ConstantTen);
        }
        /**
         * 校验排序参数
         */
        pagingParam.setSortingParam(verifySortingParamTo(pagingParam.getSortingParam(), verifyClass, sorting));
    }

    /**
     * 处理排序参数
     * @param sortingParam 参数集合
     * @return 处理结果(返回排序Sql语句)
     */
    public static String processSortingParam(List<SortingParam> sortingParam) {
        return processSortingParam(sortingParam, "");
    }

    /**
     * 处理排序参数
     * @param sortingParam 参数集合
     * @param anotherName 查询别名
     * @return 处理结果(返回排序Sql语句)
     */
    public static String processSortingParam(List<SortingParam> sortingParam, String anotherName) {
        /**
         * 创建排序语句
         */
        StringBuffer sortingSql = new StringBuffer(" order by ");
        /**
         * 处理排序查询条件
         */
        for (SortingParam item : sortingParam)
            sortingSql.append(anotherName.concat(item.getPropertyName()) + " " + item.getSortingManner() + ", ");
        return StringUtils.substringBeforeLast(sortingSql.toString(), ",");
    }

    /**
     * 拼接SelectSQL
     * @param object 实体
     * @return 校验结果
     */
    public static String SQLSelect(Object object) {
        /**
         * 定义boolean变量
         */
        String sql = "select ";
        /**
         * 通过Class的getDeclaredFields(反射机制)获取Field对象信息
         */
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field itemField : declaredFields)
            sql = sql.concat(itemField.getName()).concat(",");
        return sql.substring(0, sql.length() - 1);
    }

    /**
     * 校验主键参数
     * @param key 主键参数
     */
    public static void verifyMainKey(Integer key) {
        if (!(key > ConstantParam.ConstantZero)) throw new OpenException("错误的主键值", ResultEnum.ClientError);
    }

    /**
     * 校验状态参数
     * @param status 状态参数
     */
    public static void verifyStatus(Integer status) {
        if (!(status >= ConstantParam.ConstantZero && status < ConstantParam.ConstantTwo))
            throw new OpenException("错误的状态值", ResultEnum.ClientError);
    }

    /**
     * 私有构造器
     * -- 禁止初始化实例
     * -- 建议使用类名.方法使用
     */
    private VerifyTool() {

    }

}
