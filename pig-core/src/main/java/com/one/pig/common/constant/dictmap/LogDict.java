package com.one.pig.common.constant.dictmap;

import com.one.pig.common.constant.dictmap.base.AbstractDictMap;

/**
 * 日志的字典
 *
 * @author csy
 * @date 2017-05-06 15:01
 */
public class LogDict extends AbstractDictMap {

    @Override
    public void init() {
        put("tips","备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}
