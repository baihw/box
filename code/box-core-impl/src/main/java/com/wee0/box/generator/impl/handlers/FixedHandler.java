package com.wee0.box.generator.impl.handlers;

import com.wee0.box.generator.IGenerateHandler;
import com.wee0.box.generator.IGenerator;
import com.wee0.box.util.shortcut.StringUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 7:02
 * @Description 返回固定值
 * <pre>
 * 补充说明
 * </pre>
 **/
public class FixedHandler implements IGenerateHandler {
    @Override
    public String getName() {
        return "Fixed";
    }

    @Override
    public Object generate(Object... args) {
        if (null == args || args.length == 0) {
            return "";
        }
        return StringUtils.joinString(IGenerator.DEF_ARGS_SPLIT_REGEX, args);
    }

}
