package com.wee0.box.validator.impl.handlers;

import com.wee0.box.struct.impl.SimpleOptions;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.validator.IValidateHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/11 7:22
 * @Description 一个基于正则表达式的数据校验处理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class RegexHandler implements IValidateHandler {
    @Override
    public String getName() {
        return "RE";
    }

    @Override
    public void validate(Object value, String... options) {
        if (null == value || null == options || 0 == options.length)
            return;
        
        SimpleOptions _options = new SimpleOptions(options);
        String _regExpression = _options.getStringByIndex(0);
        boolean _caseSensitive = _options.getBooleanByIndex(1);

        final String _valueString = String.valueOf(value);
        Pattern _pattern = _caseSensitive ? Pattern.compile(_regExpression) : Pattern.compile(_regExpression, Pattern.CASE_INSENSITIVE);
        Matcher _matcher = _pattern.matcher(_valueString);

        CheckUtils.checkArgument(!_matcher.matches(), "value '%s' dose not match the regex '%s'!", _valueString, _regExpression);
    }
}
