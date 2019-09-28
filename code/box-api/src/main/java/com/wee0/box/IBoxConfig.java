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

package com.wee0.box;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:22
 * @Description box框架配置对象
 * <pre>
 * 注意区分IBoxConfig与IConfig对象的区别：
 * IBoxConfig管理的是框架相关的配置信息，
 * 修改调整的时机大概率发生在产品开发前的插件选型与开发过程中的调整时刻，
 * 面向的用户是开发人员。
 *
 * IConfig管理的是项目相关的配置信息，
 * 修改调整的时机大概率发生在产品开发完成后的部署运行时刻，
 * 面向的用户是运维部署人员。
 * </pre>
 **/
public interface IBoxConfig {

    /**
     * 获取指定键名对应的配置值
     *
     * @param key 键名
     * @return 配置值
     */
    String get(String key);

    /**
     * 获取指定键名对应的配置值
     *
     * @param key      键名
     * @param defValue 当值不存在或为空时返回的默认值
     * @return 配置值
     */
    String get(String key, String defValue);

    /**
     * 获取资源文件存储目录
     *
     * @return 资源文件存储目录
     */
    String getResourceDir();

    /**
     * 获取指定资源的路径
     *
     * @param resource 资源名称
     * @return 资源路径
     */
    String getResourcePath(String resource);

    /**
     * 获取指定资源的输入流
     *
     * @param resource 资源名称
     * @return 资源输入流
     * @throws IOException 读取异常
     */
    InputStream getResourceAsStream(String resource) throws IOException;

    /**
     * 获取存在配置信息的指定接口实现类单例对象
     *
     * @param interfaceClass 接口类
     * @param <T>            类型
     * @return 实现类单例对象
     */
    <T> T getInterfaceImpl(Class<T> interfaceClass);

    /**
     * 获取所有实现类单例对象
     *
     * @return 所有实现类单例对象
     */
    Collection getAllInterfaceImpl();

}
