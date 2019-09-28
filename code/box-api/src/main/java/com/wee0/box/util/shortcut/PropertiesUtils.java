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

package com.wee0.box.util.shortcut;

import com.wee0.box.BoxConfig;
import com.wee0.box.util.IPropertiesUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:08
 * @Description Properties配置文件处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class PropertiesUtils {

    // 实现类实例
    private static final IPropertiesUtils IMPL = BoxConfig.impl().getInterfaceImpl(IPropertiesUtils.class);


    /**
     * 从输入流加载数据初始化
     *
     * @param inputStream 输入流，需要调用者自行关闭
     * @return 配置对象
     */
    public static Properties load(InputStream inputStream) {
        return IMPL.load(inputStream);
    }

    /**
     * 从文件加载数据初始化
     *
     * @param file 文件
     * @return 配置对象
     */
    public static Properties load(File file) {
        return IMPL.load(file);
    }

    /**
     * 转换为Map对象
     *
     * @param properties 配置对象
     * @return Map对象
     */
    public static Map<String, String> toMap(Properties properties) {
        return IMPL.toMap(properties);
    }

    /**
     * 从输入流加载数据并转换为Map对象
     *
     * @param inputStream 输入流，需要调用者自行关闭
     * @return Map对象
     */
    public static Map<String, String> loadToMap(InputStream inputStream) {
        return IMPL.loadToMap(inputStream);
    }

    /**
     * 从文件加载数据并转换为Map对象
     *
     * @param file 文件
     * @return Map对象
     */
    public static Map<String, String> loadToMap(File file) {
        return IMPL.loadToMap(file);
    }

}
