package com.wee0.box.validator.impl.handlers;

import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.validator.IValidateHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/11 7:22
 * @Description 一个针对整数的数据校验处理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class IntHandler implements IValidateHandler {

    static final String PATTERN_INT = "\\d+";
    /**
     * 正则表达式字符串--数字类型限定长度,需要两个参数,最小值,最大值.
     */
    static final String PATTERN_INT_N_N = "\\d{%1$s,%2$s}"; // {6,12}
    static final Pattern _PATTERN = Pattern.compile(PATTERN_INT);

    @Override
    public String getName() {
        return "int";
    }

    @Override
    public void validate(Object value, String... options) {
        if (null == value)
            return;

        final String _valueString = String.valueOf(value);
        Matcher _matcher = _PATTERN.matcher(_valueString);
        CheckUtils.checkArgument(!_matcher.matches(), "value '%s' is not a valid integer!", _valueString);
    }
}
