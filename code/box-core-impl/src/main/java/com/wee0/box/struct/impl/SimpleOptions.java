package com.wee0.box.struct.impl;

import com.wee0.box.struct.IOptions;
import com.wee0.box.util.shortcut.CheckUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/11 7:36
 * @Description 一个简单的选项管理对象实现。
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleOptions implements IOptions {

    // 选项集合
    private final Object[] _options;

    public SimpleOptions(Object... options) {
        this._options = (null == options || 0 == options.length) ? new Object[0] : options;
    }

    public String getStringByIndex(int index) {
        Object _option = getByIndex(index);
        if (null == _option)
            return null;
        return String.valueOf(_option);
    }

    public Boolean getBooleanByIndex(int index) {
        Object _option = getByIndex(index);
        if (null == _option)
            return null;
        return Boolean.parseBoolean(String.valueOf(_option));
    }

    public Integer getIntByIndex(int index) {
        Object _option = getByIndex(index);
        if (null == _option)
            return null;
        try {
            return Integer.parseInt(String.valueOf(_option));
        } catch (NumberFormatException e) {
            CheckUtils.checkArgument(true, "index '%s' value is not an integer!", index);
        }
        return null;
    }

    private Object getByIndex(int index) {
        CheckUtils.checkIndex(index, _options.length);
        return _options[index];
    }
}
