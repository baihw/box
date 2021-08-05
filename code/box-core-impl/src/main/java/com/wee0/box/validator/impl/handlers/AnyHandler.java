package com.wee0.box.validator.impl.handlers;

import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.validator.IValidateHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/11 7:22
 * @Description 一个任意非空数据都可通过的数据校验处理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class AnyHandler implements IValidateHandler {

    /**
     * 正则表达式字符串--任意类型,任何字符,不留空即可通过验证
     */
    static final String PATTERN_ANY = "[\\w\\W]+";
    static final Pattern _PATTERN = Pattern.compile(PATTERN_ANY);

    @Override
    public String getName() {
        return "any";
    }

    @Override
    public void validate(Object value, String... options) {
        if (null == value)
            return;

        final String _valueString = String.valueOf(value);
        Matcher _matcher = _PATTERN.matcher(_valueString);
        CheckUtils.checkArgument(!_matcher.matches(), "'%s' is not any valid value!", _valueString);
    }
}
