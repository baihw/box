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
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.plugin.IPlugin;
import com.wee0.box.plugin.IPluginLoader;
import com.wee0.box.plugin.IPluginManager;
import com.wee0.box.util.IHttpUtils;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.HttpUtils;
import com.wee0.box.util.shortcut.ZipUtils;

import java.io.File;
import java.io.ObjectStreamException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 20:56
 * @Description 一个简单的插件管理器实现
 * <pre>
 * 参考配置：
 * # 声明使用此插件管理器实现
 * com.wee0.box.plugin.IPluginManager=com.wee0.box.plugin.impl.SimpleHttpPluginManager
 * # 插件仓库地址
 * # plugin.repository=http://repo.wee0.com/box/plugins
 * # 依赖的插件集合，多个之间用逗号隔开
 * # plugin.dependencies=office-poi:0.1.0,storage-oss:0.1.0
 * # 插件自定义参数配置，具体配置方法参考选择的插件使用说明
 * # plugin.storage-oss.accessKeyId=123
 * </pre>
 **/
public class SimpleHttpPluginManager implements IPluginManager {

    // 默认的插件仓库地址
    public static final String DEF_REPOSITORY = "http://repo.wee0.com/box/plugins";

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleHttpPluginManager.class);

    // 插件存储根目录名称
    private static final String NAME_PLUGINS_DIR = "plugins";

    // 插件配置参数前缀
    private static final String KEY_PREFIX = BoxConfigKeys.pluginPrefix;
    // 插件配置参数关键字：使用的插件仓库地址
    private static final String KEY_REPOSITORY = KEY_PREFIX + "repository";
    // 插件配置参数关键字：使用的插件标识名称集合
    private static final String KEY_DEPENDENCIES = KEY_PREFIX + "dependencies";

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
    public synchronized void start() {
        if (null != this._loaders) {
            log.warn("PluginManager already started.");
            return;
        }
        String _repository = BoxConfig.impl().get(KEY_REPOSITORY, DEF_REPOSITORY);
        log.debug("repository: {}", _repository);

        String _dependenciesString = BoxConfig.impl().get(KEY_DEPENDENCIES, null);
        String[] _dependencies;
        if (null == _dependenciesString || 0 == (_dependencies = _dependenciesString.split(",")).length) {
            log.debug("plugin dependencies is empty.");
            this._loaders = new LinkedHashMap<>(8, 1.0F);
            return;
        }

        this._loaders = new LinkedHashMap<>(_dependencies.length, 1.0F);

        File _plugins_dir = new File(BoxConfig.impl().getExternalDir(), NAME_PLUGINS_DIR);
        if (!_plugins_dir.exists() || !_plugins_dir.isDirectory()) {
            _plugins_dir = new File(BoxConfig.impl().getResourceDir(), NAME_PLUGINS_DIR);
            if (!_plugins_dir.exists() || !_plugins_dir.isDirectory()) {
                _plugins_dir = new File(System.getProperty("user.dir"), ".box/plugins");
                if (!_plugins_dir.exists()) _plugins_dir.mkdirs();
            }
        }
        log.debug("plugins dir:{}", _plugins_dir);

        final ClassLoader _PARENT_LOADER = Thread.currentThread().getContextClassLoader();
        for (String _dependency : _dependencies) {
            if (3 > (_dependency = _dependency.trim()).length()) continue;
            int _ndx = _dependency.indexOf(':');
            if (1 > _ndx) {
                log.warn("Invalid dependency: {}", _dependency);
                continue;
            }
            String _dependencyId = _dependency.substring(0, _ndx);
            String _dependencyVersion = _dependency.substring(_ndx + 1);
            log.debug("find dependency id：{}，version：{} ", _dependencyId, _dependencyVersion);

            File _dependencyDir = new File(_plugins_dir, _dependencyId + "/" + _dependencyVersion);
            if (!_dependencyDir.exists()) _dependencyDir.mkdirs();
            if (1 > _dependencyDir.listFiles().length) {
                String _dependencyZipFileName = _buildDependencyZipName(_dependencyId, _dependencyVersion);
                File _dependencyZipFile = new File(_dependencyDir, _dependencyZipFileName);
                if (!_dependencyZipFile.exists()) {
                    String _dependencyZipUrl = _buildDownloadUrl(_repository, _dependencyId, _dependencyVersion);
                    log.debug("download dependency id: {}, version: {}", _dependencyId, _dependencyVersion);
                    IHttpUtils.IHttpResult _httpResult = HttpUtils.impl().httpDownload(_dependencyZipUrl, _dependencyZipFile);
                    log.debug("download result: {}", _httpResult);
                    if (null == _httpResult || _httpResult.getCode() >= 400)
                        throw new IllegalStateException("download error for plugin: " + _dependencyId + ", version: " + _dependencyVersion);
                }
                ZipUtils.impl().decompress(_dependencyZipFile, _dependencyDir);
            }

            final String _ID_PREFIX = KEY_PREFIX.concat(_dependencyId).concat(".");
            final Map<String, String> _CONFIG = BoxConfig.impl().getByPrefix(_ID_PREFIX);
            SimpleHttpPluginLoader _loader = new SimpleHttpPluginLoader(_dependencyDir, _dependencyId, _CONFIG, _PARENT_LOADER);
            log.debug("init plugin：{}，loader：{} ", _dependencyId, _loader);
            // 默认主动初始化
            _loader.init();
            String _interfaceName = _loader.getInterfaceName();
            if (null == _interfaceName)
                throw new IllegalStateException("not found implementation interface name by plugin: " + _dependencyId);
            if (this._loaders.containsKey(_interfaceName))
                throw new IllegalStateException("duplicate register interface name: " + _interfaceName + " by plugin: " + _dependencyId);
            this._loaders.put(_interfaceName, _loader);
//            Map<String, String> _interfaces = _loader.getInterfaces();
//            if (null == _interfaces || _interfaces.isEmpty())
//                throw new IllegalStateException("not found any interface implementation by plugin: " + _dependencyId);
//            Iterator<Map.Entry<String, String>> _entries = _interfaces.entrySet().iterator();
//            while (_entries.hasNext()) {
//                Map.Entry<String, String> _entry = _entries.next();
//                String _entryKey = _entry.getKey();
//                String _entryVal = _entry.getValue();
//                if (this._loaders.containsKey(_entryKey))
//                    throw new IllegalStateException("duplicate register plugin interface: " + _entryKey + " impl: " + _entryVal);
//                this._loaders.put(_entryKey, _loader);
//            }
        }
    }

    @Override
    public synchronized void stop() {
        if (null == this._loaders) {
            log.warn("PluginManager already stopped.");
            return;
        }
        this._loaders.forEach((_id, _loader) -> {
            log.debug("destroy plugin: {}", _id);
            _loader.destroy();
        });
        this._loaders.clear();
        this._loaders = null;
    }

    private static String _buildDownloadUrl(String repository, String id, String version) {
        return repository + "/" + id + "/" + version + "/" + id + "-" + version + ".zip";
    }

    // 构建依赖压缩包完整名称。
    private static String _buildDependencyZipName(String id, String version) {
        return id + "-" + version + ".zip";
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleHttpPluginManager() {
        if (null != SimplePluginManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimplePluginManagerHolder {
        private static final SimpleHttpPluginManager _INSTANCE = new SimpleHttpPluginManager();
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
    public static SimpleHttpPluginManager me() {
        return SimplePluginManagerHolder._INSTANCE;
    }

}
