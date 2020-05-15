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

package com.wee0.box.plugin;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/22 17:05
 * @Description 插件管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IPluginManager {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.plugin.impl.SimplePluginManager";

    /**
     * 获取插件接口实现者实例对象
     *
     * @param pluginInterface 插件接口
     * @param <T>             插件接口类型
     * @return 插件接口实现者实例
     */
    <T extends IPlugin> T getPlugin(Class<T> pluginInterface);

    /**
     * 启动插件管理器
     */
    void start();

    /**
     * 停止插件管理器
     */
    void stop();

}
