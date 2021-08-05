package com.wee0.box.generator.impl.handlers;

import com.wee0.box.generator.IGenerateHandler;

import java.util.UUID;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 7:02
 * @Description UUID生成
 * <pre>
 * 补充说明
 * </pre>
 **/
public class UuidGenerateHandler implements IGenerateHandler {
    @Override
    public String getName() {
        return "UUID";
    }

    @Override
    public Object generate(Object... args) {
        String _uuid = UUID.randomUUID().toString();
        if (null != args && 0 != args.length) {
            String _type = String.valueOf(args[0]);
            if ("32".equals(_type)) {
                _uuid = _uuid.replace("-", "");
            }
        }
        return _uuid;
    }
}
