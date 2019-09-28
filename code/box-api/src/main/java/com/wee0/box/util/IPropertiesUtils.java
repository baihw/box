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

package com.wee0.box.util;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:59
 * @Description Properties配置文件处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IPropertiesUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimplePropertiesUtils";

    /**
     * 从输入流加载数据初始化
     *
     * @param inputStream 输入流，需要调用者自行关闭
     * @return 配置对象
     */
    Properties load(InputStream inputStream);

    /**
     * 从文件加载数据初始化
     *
     * @param file 文件
     * @return 配置对象
     */
    Properties load(File file);

    /**
     * 转换为Map对象
     *
     * @param properties 配置对象
     * @return Map对象
     */
    Map<String, String> toMap(Properties properties);

    /**
     * 从输入流加载数据并转换为Map对象
     *
     * @param inputStream 输入流，需要调用者自行关闭
     * @return Map对象
     */
    Map<String, String> loadToMap(InputStream inputStream);

    /**
     * 从文件加载数据并转换为Map对象
     *
     * @param file 文件
     * @return Map对象
     */
    Map<String, String> loadToMap(File file);

}
