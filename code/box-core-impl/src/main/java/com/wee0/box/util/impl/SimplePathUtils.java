/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.util.impl;

import com.wee0.box.util.IPathUtils;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.File;
import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/6/6 7:32
 * @Description 一个简单的路径处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimplePathUtils implements IPathUtils {

    // 文件路径分隔符号匹配正则表达式
    static final String REG_FILE_SEPARATOR = "\\".equals(File.separator) ? "\\\\+" : File.separator + "+";


    @Override
    public String separatorToForwardSlash(String path) {
        path = CheckUtils.checkTrimEmpty(path, null);
        if (null == path) return null;
        if (-1 != path.indexOf('/'))
            path = path.replaceAll("/{2,}", "/");
        if (-1 != path.indexOf('\\'))
            path = path.replaceAll("\\\\+", "/");
        return path;
    }

    @Override
    public String separatorToBackSlash(String path) {
        path = CheckUtils.checkTrimEmpty(path, null);
        if (null == path) return null;
        if (-1 != path.indexOf('\\'))
            path = path.replaceAll("\\\\+", "\\\\\\\\");
        path = path.replaceAll("/+", "\\\\\\\\");
        return path;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimplePathUtils() {
        if (null != SimplePathUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimplePathUtilsHolder {
        private static final SimplePathUtils _INSTANCE = new SimplePathUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimplePathUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimplePathUtils me() {
        return SimplePathUtilsHolder._INSTANCE;
    }
}
