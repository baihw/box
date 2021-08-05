package com.wee0.box.validator.impl;

import com.wee0.box.util.impl.UtilsCandidate;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.validator.IValidateHandler;
import com.wee0.box.validator.IValidator;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 7:52
 * @Description 一个简单的数据规则校验器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleValidator implements IValidator {

    // 数据生成处理器实现对象集合。
    private final Map<String, IValidateHandler> _HANDLERS;


    @Override
    public void validate(Object value, String... patterns) {
        if (null == patterns || 0 == patterns.length)
            return;
        for (String _pattern : patterns) {
            doValidatePattern(value, _pattern);
        }
    }

    private void doValidatePattern(Object value, String pattern) {
        List<String> _nameArgsData = UtilsCandidate.parseNameArgs(pattern, DEF_NAME_SEPARATOR, DEF_ARGS_SPLIT_REGEX);
        String _first = _nameArgsData.remove(0).toUpperCase();
        IValidateHandler _validateHandler = _HANDLERS.get(_first);
        CheckUtils.checkNotNull(_validateHandler, "unknown validateHandler name：%s", _first);
        if (_nameArgsData.isEmpty()) {
            _validateHandler.validate(value, pattern);
        } else {
            _validateHandler.validate(value, _nameArgsData.toArray(new String[_nameArgsData.size()]));
        }
    }

    @Override
    public void addValidateHandler(IValidateHandler validateHandler) {
        if (null == validateHandler)
            return;
        String _name = CheckUtils.checkNotTrimEmpty(validateHandler.getName(), "validateHandler name cannot be empty!");
        this._HANDLERS.put(_name.toUpperCase(), validateHandler);
    }


    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleValidator() {
        if (null != SimpleValidatorHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        this._HANDLERS = new HashMap<>(16, 1.0f);
        // 初始化内置的数据校验处理器
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleValidatorHolder {
        private static final SimpleValidator _INSTANCE = new SimpleValidator();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleValidatorHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleValidator me() {
        return SimpleValidatorHolder._INSTANCE;
    }
}
