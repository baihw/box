package com.wee0.box.util.impl;

import com.wee0.box.util.IObjectUtils;

import java.io.ObjectStreamException;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/29 7:06
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleObjectUtils implements IObjectUtils {
    @Override
    public void setProperty(Object bean, String name, Object value) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public void setProperties(Object bean, Map<String, ?> properties) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public Map<String, Object> toMap(Object bean) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public String reflectionToString(Object bean) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleObjectUtils() {
        if (null != SimpleObjectUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleObjectUtilsHolder {
        private static final SimpleObjectUtils _INSTANCE = new SimpleObjectUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleObjectUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleObjectUtils me() {
        return SimpleObjectUtilsHolder._INSTANCE;
    }
}
