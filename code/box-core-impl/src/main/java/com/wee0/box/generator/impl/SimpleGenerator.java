package com.wee0.box.generator.impl;

import com.wee0.box.generator.IGenerateHandler;
import com.wee0.box.generator.IGenerator;
import com.wee0.box.generator.impl.handlers.DateTimeHandler;
import com.wee0.box.generator.impl.handlers.FixedHandler;
import com.wee0.box.generator.impl.handlers.UuidGenerateHandler;
import com.wee0.box.util.impl.UtilsCandidate;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 6:56
 * @Description 一个简单的数据生成器实现
 * <pre>
 * 一个简单的组合数据生成器实现：SimpleComposeGenerator
 * </pre>
 **/
public class SimpleGenerator implements IGenerator {

    // 数据生成处理器实现对象集合。
    private final Map<String, IGenerateHandler> _GENERATORS;

    @Override
    public Object generate(String rule) {
        List<String> _ruleData = UtilsCandidate.parseNameArgs(rule, DEF_NAME_SEPARATOR, DEF_ARGS_SPLIT_REGEX);
        String _first = _ruleData.remove(0).toUpperCase();
        IGenerateHandler _generateHandler = _GENERATORS.get(_first);
        if (null == _generateHandler) {
            if (0 == _ruleData.size()) {
                // 如果没有指定名称的生成器，并且没有提供任何参数元素，则视作用户希望使用此值作为固定的默认值，直接返回。
                return _first;
            }
            // 如果有指定参数元素，则报错找不到指定名称的生成器。
            CheckUtils.checkArgument(true, "unknown generateHandler name：%s", _first);
        }
//        CheckUtils.checkNotNull(_generateHandler, "unknown generateHandler name：%s", _ruleName);
        if (_ruleData.isEmpty()) {
            return _generateHandler.generate();
        } else {
            return _generateHandler.generate(_ruleData.toArray(new Object[_ruleData.size()]));
        }
    }

    @Override
    public void addGenerateHandler(IGenerateHandler generateHandler) {
        if (null == generateHandler)
            return;
        String _name = CheckUtils.checkNotTrimEmpty(generateHandler.getName(), "generateHandler name cannot be empty!");
        this._GENERATORS.put(_name.toUpperCase(), generateHandler);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleGenerator() {
        if (null != SimpleComposeGeneratorHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        this._GENERATORS = new HashMap<>(16, 1.0f);
        // 初始化内置的数据生成处理器
        this.addGenerateHandler(new FixedHandler());
        this.addGenerateHandler(new UuidGenerateHandler());
        this.addGenerateHandler(new DateTimeHandler());
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleComposeGeneratorHolder {
        private static final SimpleGenerator _INSTANCE = new SimpleGenerator();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleComposeGeneratorHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleGenerator me() {
        return SimpleComposeGeneratorHolder._INSTANCE;
    }
}
