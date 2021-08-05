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

import com.wee0.box.BoxConfig;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.plugin.IPlugin;
import com.wee0.box.plugin.IPluginLoader;
import com.wee0.box.plugin.IPluginManager;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.File;
import java.io.ObjectStreamException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/28 7:22
 * @Description 一个简单的插件管理器实现
 * <pre>
 * 参考配置：
 * # 声明使用此插件管理器实现
 * com.wee0.box.plugin.IPluginManager=com.wee0.box.plugin.impl.SimpleLocalPluginManager
 * # 使用的插件标识集合，多个之间用逗号隔开
 * plugin.ids=minio
 * # 继承自IPlugin接口的插件主接口完全限定名称
 * plugin.minio.interface=com.wee0.box.plugins.storage.ICloudStoragePlugin
 * # 插件主接口实现类完全限定名称
 * plugin.minio.impl=com.wee0.box.plugins.storage.minio.MinioStorage
 * # 插件配置，具体配置方法参考选择的插件管理器使用说明
 * # plugin.minio.xx=xx
 * # plugin.minio.xx=xx
 * </pre>
 **/
public class SimpleLocalPluginManager implements IPluginManager {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleLocalPluginManager.class);

    // 插件存储根目录名称
    private static final String NAME_PLUGINS_DIR = "plugins";

    // 插件配置参数前缀
    private static final String KEY_PREFIX = "plugin.";
    // 插件配置参数关键字：使用的插件标识名称集合
    private static final String KEY_IDS = KEY_PREFIX + "ids";

    // 插件配置参数关键字：继承自IPlugin接口的插件主接口完全限定名称
    private static final String KEY_INTERFACE = "interface";
    // 插件配置参数关键字：插件主接口实现类完全限定名称
    private static final String KEY_IMPL = "impl";

    // 插件类加载器集合
    private Map<String, IPluginLoader> _loaders;
//    // 插件实例对象
//    private final Map<String, Object> _instances = new HashMap<>(8, 1.0F);

    @Override
    public <T extends IPlugin> T getPlugin(Class<T> pluginInterface) {
        CheckUtils.checkNotNull(pluginInterface, "pluginInterface can not be null!");
        if (!IPlugin.class.isAssignableFrom(pluginInterface))
            throw new IllegalArgumentException("pluginInterface must be extends IPlugin.");
        if (!this._loaders.containsKey(pluginInterface.getName()))
            throw new IllegalStateException("not found plugin by " + pluginInterface);
        return (T) _loaders.get(pluginInterface.getName()).getPlugin();
    }

    @Override
    public void start() {
        if (null != this._loaders) {
            log.warn("PluginManager already started.");
            return;
        }
        synchronized (this) {
            if (null != this._loaders)
                return;
            String _ids_value = BoxConfig.impl().get(KEY_IDS, null);
            String[] _ids;
            if (null == _ids_value || 0 == (_ids = _ids_value.split(",")).length) {
                log.debug("plugin ids is empty.");
                this._loaders = new LinkedHashMap<>(8, 1.0F);
                return;
            }
            final ClassLoader _PARENT_LOADER = Thread.currentThread().getContextClassLoader();
            File _plugins_dir = new File(BoxConfig.impl().getExternalDir(), NAME_PLUGINS_DIR);
            if (!_plugins_dir.exists() || !_plugins_dir.isDirectory()) {
                _plugins_dir = new File(BoxConfig.impl().getResourceDir(), NAME_PLUGINS_DIR);
                if (!_plugins_dir.exists() || !_plugins_dir.isDirectory()) {
                    throw new IllegalStateException("Invalid plugins dir:" + _plugins_dir);
                }
            }
            log.debug("plugins dir:{}", _plugins_dir);
            this._loaders = new LinkedHashMap<>(_ids.length, 1.0F);
            for (String _id : _ids) {
                if (0 == (_id = _id.trim()).length())
                    continue;
                final String _ID_PREFIX = KEY_PREFIX.concat(_id).concat(".");
                final Map<String, String> _CONFIG = BoxConfig.impl().getByPrefix(_ID_PREFIX);
                final String _INTERFACE = _CONFIG.get(KEY_INTERFACE);
                final String _IMPL = _CONFIG.get(KEY_IMPL);
                File _DIR = new File(_plugins_dir, _id);
                SimpleLocalPluginLoader _loader = new SimpleLocalPluginLoader(_id, _INTERFACE, _IMPL, _CONFIG, _DIR, _PARENT_LOADER);
                log.debug("init plugin：{}，loader：{} ", _id, _loader);
                // 默认主动初始化
                _loader.init();
                this._loaders.put(_INTERFACE, _loader);
            }
        }
    }

    @Override
    public void stop() {
        if (null == this._loaders) {
            log.warn("PluginManager already stopped.");
            return;
        }
        synchronized (this) {
            if (null == this._loaders)
                return;
            this._loaders.forEach((_id, _loader) -> {
                log.debug("destroy plugin: {}", _id);
                _loader.destroy();
            });
            this._loaders.clear();
            this._loaders = null;
        }

    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleLocalPluginManager() {
        if (null != SimplePluginManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimplePluginManagerHolder {
        private static final SimpleLocalPluginManager _INSTANCE = new SimpleLocalPluginManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimplePluginManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleLocalPluginManager me() {
        return SimplePluginManagerHolder._INSTANCE;
    }

}
