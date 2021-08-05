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

package com.wee0.box.plugin.impl;

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.plugin.IPlugin;
import com.wee0.box.plugin.IPluginLoader;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/28 7:32
 * @Description 一个简单的本地插件类加载器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class SimpleLocalPluginLoader<T extends IPlugin> implements IPluginLoader<T> {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleLocalPluginLoader.class);

    // 插件标识
    private String plugin_id;
    // 插件接口
    private String plugin_interface_name;
    // 插件接口实现类
    private String plugin_impl_name;
    // 插件配置数据
    private Map<String, String> plugin_config;
    // 插件存储目录
    private File plugin_dir;

    // 父类加载器
    private ClassLoader parentLoader;
    // 插件类加载器
    private ClassLoader jarClassLoader;

    // 插件接口类
    private Class plugin_interface;
    // 插件实现类实例
    private T plugin_impl;

    SimpleLocalPluginLoader(String id, String interfaceName, String implName, Map<String, String> config, File dir, ClassLoader parentLoader) {
        this.plugin_id = CheckUtils.checkNotTrimEmpty(id, "id can not be empty!");
        this.plugin_interface_name = CheckUtils.checkNotTrimEmpty(interfaceName, "interfaceName can not be empty!");
        this.plugin_impl_name = CheckUtils.checkNotTrimEmpty(implName, "implName can not be empty!");
        this.plugin_config = config;
        this.plugin_dir = dir;
        this.parentLoader = null == parentLoader ? Thread.currentThread().getContextClassLoader() : parentLoader;
    }

    // 插件初始化
    public synchronized void init() {
        if (!this.plugin_dir.exists()) {
            throw new IllegalStateException("not found plugin dir: " + this.plugin_dir);
        }
        if (null != this.jarClassLoader)
            return;

        List<URL> _jarUrlList = new ArrayList<>(32);
        addJarUrls(_jarUrlList, this.plugin_dir);
        // 检查是否存在lib目录，如果存在，增加lib目录下的所有jar文件到类加载器。
        File _libDir = new File(this.plugin_dir, "lib");
        addJarUrls(_jarUrlList, _libDir);

        if (_jarUrlList.isEmpty()) {
            throw new IllegalStateException("empty plugin dir: " + this.plugin_dir);
        }

        log.trace("plugin: {}, jars: {}", this.plugin_id, _jarUrlList);
        URL[] jarUrls = _jarUrlList.toArray(new URL[]{});
        this.jarClassLoader = new URLClassLoader(jarUrls, this.parentLoader);

        // 初始化插件实例
        try {
//            this.plugin_interface = this.jarClassLoader.loadClass(this.plugin_interface_name);
            this.plugin_interface = Class.forName(this.plugin_interface_name, true, this.jarClassLoader);
            if (null == this.plugin_interface || !IPlugin.class.isAssignableFrom(this.plugin_interface))
                throw new IllegalStateException("Invalid IPlugin interface: " + this.plugin_interface);
            Class _impl_class = Class.forName(this.plugin_impl_name, true, this.jarClassLoader);
            if (null == _impl_class || !this.plugin_interface.isAssignableFrom(_impl_class))
                throw new IllegalStateException("Invalid IPlugin impl: " + _impl_class);
//            Method _meMethod = _impl_class.getDeclaredMethod("me");
//            // 通过静态方法"me"获取实现类实例单例对象。方法签名为：public static T me(){} ;
//            Object _result = _meMethod.invoke(null);

//            this.plugin_impl = (T) _impl_class.newInstance();

            Constructor _constructor = _impl_class.getDeclaredConstructor(null);
            _constructor.setAccessible(true);
            this.plugin_impl = (T) _constructor.newInstance(null);
            this.plugin_impl.init(this.plugin_config);
            log.debug("plugin {} impl: {}", this.plugin_id, this.plugin_impl);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void destroy() {
        if (null == this.jarClassLoader)
            return;
        if (null != this.plugin_impl)
            this.plugin_impl.destroy();
        if (this.jarClassLoader instanceof URLClassLoader) {
            try {
                ((URLClassLoader) this.jarClassLoader).close();
            } catch (IOException e) {
                log.warn("close error!", e);
            }
        }
    }

    @Override
    public T getPlugin() {
        return this.plugin_impl;
    }

    private static void addJarUrls(List<URL> urls, File dir) {
        if (dir.exists() && dir.isDirectory()) {
            File[] _files = dir.listFiles();
            if (null != _files && 0 != _files.length) {
                for (File _file : _files) {
                    if (_file.isFile() && _file.getName().endsWith(".jar")) {
                        try {
                            URL _jarUrl = _file.toURI().toURL();
                            urls.add(_jarUrl);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

}
