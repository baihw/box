package com.wee0.box.validator.impl.handlers;

import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.validator.IValidateHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/11 7:22
 * @Description 一个针对URL地址的数据校验处理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class UrlHandler implements IValidateHandler {

    static final String PATTERN_URL = "(\\w+:\\/\\/)?\\w+(\\.\\w+)+.*";
    static final Pattern _PATTERN = Pattern.compile(PATTERN_URL);

    @Override
    public String getName() {
        return "url";
    }

    @Override
    public void validate(Object value, String... options) {
        if (null == value)
            return;

        final String _valueString = String.valueOf(value);
        Matcher _matcher = _PATTERN.matcher(_valueString);
        CheckUtils.checkArgument(!_matcher.matches(), "value '%s' is not a valid url!", _valueString);
    }
}
