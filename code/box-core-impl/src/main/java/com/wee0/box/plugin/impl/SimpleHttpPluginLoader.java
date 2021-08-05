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
import com.wee0.box.util.shortcut.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 20:25
 * @Description 一个简单的基于Http请求的插件类加载器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleHttpPluginLoader<T extends IPlugin> implements IPluginLoader<T> {


    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleLocalPluginLoader.class);

    // 插件接口
    // Implementation-Interfaces
    static final String KEY_INTERFACE = "pluginInterface";
    // 插件接口实现类
    static final String KEY_IMPL = "pluginImplementation";
    // 插件配置文件位置
    static final String PLUGIN_FILE = "META-INF/box/%s.json";

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

    SimpleHttpPluginLoader(File dir, String id, Map<String, String> config, ClassLoader parentLoader) {
        if (null == dir || !dir.exists()) throw new IllegalStateException("Invalid plugin dir: " + dir);
        this.plugin_dir = dir;
        this.plugin_id = CheckUtils.checkNotTrimEmpty(id, "id can not be empty!");
        this.plugin_config = new HashMap<>(16, 1.0F);
        this.parentLoader = null == parentLoader ? Thread.currentThread().getContextClassLoader() : parentLoader;

        List<URL> _jarUrlList = new ArrayList<>(32);
        addJarUrls(_jarUrlList, this.plugin_dir);
        if (_jarUrlList.isEmpty()) {
            throw new IllegalStateException("empty plugin dir: " + this.plugin_dir);
        }
        log.trace("plugin: {}, jars: {}", this.plugin_id, _jarUrlList);
        URL[] _jarUrls = _jarUrlList.toArray(new URL[]{});
        this.jarClassLoader = new URLClassLoader(_jarUrls, this.parentLoader);

        URL _jsonUrl = this.jarClassLoader.getResource("META-INF/box/" + this.plugin_id + ".json");
//        String _jsonString = new String(Files.readAllBytes(Paths.get(_jsonUrl.toURI())));
        Map<String, Object> _jsonMap = JsonUtils.readToMap(_jsonUrl);
        _jsonMap.forEach((String _key, Object _value) -> {
            if (null == _key || 0 == (_key = _key.trim()).length())
                return;
            String _valueString = null == _value ? null : _value.toString();
            this.plugin_config.put(_key, _valueString);
        });
        if (null != config && !config.isEmpty()) this.plugin_config.putAll(config);
        this.plugin_interface_name = this.plugin_config.get(KEY_INTERFACE);
        CheckUtils.checkNotNull(this.plugin_interface_name, KEY_INTERFACE + " cannot be empty!");
        this.plugin_impl_name = this.plugin_config.get(KEY_IMPL);
        CheckUtils.checkNotNull(this.plugin_impl_name, KEY_IMPL + " cannot be empty!");
    }

    // 插件初始化
    public synchronized void init() {
        if (null != this.plugin_impl)
            return;
        // 初始化插件实例
        try {
            this.plugin_interface = this.jarClassLoader.loadClass(this.plugin_interface_name);
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

    public String getInterfaceName() {
        return this.plugin_interface_name;
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
                    } else if (_file.isDirectory() && "lib".equals(_file.getName())) {
                        // 如果是lib目录，则增加lib目录下的所有jar文件到类加载器。
                        addJarUrls(urls, _file);
                    }
                }
            }
        }
    }
}
