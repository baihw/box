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

package com.wee0.box.impl;

import com.wee0.box.BoxConfig;
import com.wee0.box.IBoxConfig;
import com.wee0.box.IBoxContext;
import com.wee0.box.cache.ICacheManager;
import com.wee0.box.code.IBizCodeManager;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.exception.IBizExceptionFactory;
import com.wee0.box.i18n.ILocale;
import com.wee0.box.io.IFileSystem;
import com.wee0.box.log.ILoggerContext;
import com.wee0.box.log.ILoggerFactory;
import com.wee0.box.sql.ISqlHelper;
import com.wee0.box.sql.ds.IDsHelper;
import com.wee0.box.sql.ds.IDsManager;
import com.wee0.box.struct.ICmdFactory;
import com.wee0.box.subject.ISubjectContext;
import com.wee0.box.util.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:23
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class SimpleBoxConfig implements IBoxConfig {

    // 文件路径分隔符号匹配正则表达式
    public static final String REG_FILE_SEPARATOR = "\\".equals(File.separator) ? "\\\\+" : File.separator + "+";

    // 资源文件根目录
    public static final String RESOURCE_DIR = SimpleBoxConfig.class.getResource("/").getPath().replaceAll(REG_FILE_SEPARATOR, "/");

    // 默认的资源文件
    public static final String DEF_RESOURCE = "config/box_config.properties";

    // 全局类加载器
    private static final ClassLoader[] CLASS_LOADERS;

    static {
        // 初始化一些第三方组件的默认配置
        System.setProperty("org.jboss.logging.provider", "slf4j");
        System.setProperty("druid.logType", "slf4j");
        System.setProperty("org.freemarker.loggerLibrary", "SLF4J");
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/test");

        // 获取全局类加载器
        List<ClassLoader> _classLoaders = new ArrayList<>(2);
        try {
            ClassLoader _claClassLoader = SimpleBoxConfig.class.getClassLoader();
            if (null != _claClassLoader)
                _classLoaders.add(_claClassLoader);
            ClassLoader _sysClassLoader = ClassLoader.getSystemClassLoader();
            if (null != _sysClassLoader)
                _classLoaders.add(_sysClassLoader);
        } catch (SecurityException se) {
        }
        CLASS_LOADERS = _classLoaders.toArray(new ClassLoader[]{});
    }

    // 数据容器
    private final Map<String, String> DATA;

    // 接口单例类实例缓存
    private final ConcurrentHashMap<Class, Object> IMPL_DATA = new ConcurrentHashMap<>(128);

    @Override
    public String get(String key) {
        return this.DATA.get(key);
    }

    @Override
    public String get(String key, String defValue) {
        String _result = this.DATA.get(key);
        return null == _result ? defValue : _result;
    }

    @Override
    public String getResourceDir() {
        return RESOURCE_DIR;
    }

    @Override
    public String getResourcePath(String resource) {
        if (null == resource)
            return null;
        resource = resource.replaceAll(REG_FILE_SEPARATOR, "/");
        if ('/' == resource.charAt(0))
            resource = resource.substring(1);
        return RESOURCE_DIR.concat(resource);
    }

    @Override
    public InputStream getResourceAsStream(String resource) throws IOException {
        if (null == resource)
            throw new IOException("resource can not be null!");
        InputStream _inStream = getResourceAsStreamByClassLoader(Thread.currentThread().getContextClassLoader(), resource);
        if (null != _inStream)
            return _inStream;
        for (ClassLoader _loader : CLASS_LOADERS) {
            _inStream = getResourceAsStreamByClassLoader(_loader, resource);
            if (null != _inStream)
                return _inStream;
        }
        throw new IOException("not found resource: " + resource);
    }

    // 使用ClassLoader加载资源文件。
    private InputStream getResourceAsStreamByClassLoader(ClassLoader classLoader, String resource) {
        InputStream _inStream = classLoader.getResourceAsStream(resource);
        if (null == _inStream && '/' != resource.charAt(0)) {
            _inStream = classLoader.getResourceAsStream("/" + resource);
        }
        return _inStream;
    }


    @Override
    public <T> T getInterfaceImpl(Class<T> interfaceClass) {
        if (null == interfaceClass)
            throw new IllegalArgumentException("interfaceClass can't be null!");
        if (!interfaceClass.isInterface())
            throw new IllegalStateException("interfaceClass must be an interface!");
        if (IMPL_DATA.containsKey(interfaceClass))
            return (T) IMPL_DATA.get(interfaceClass);
        String _className = interfaceClass.getName();
        String _implClassName = this.DATA.get(_className);
        if (null == _implClassName)
            throw new IllegalStateException("not found value by " + _className);
        synchronized (IMPL_DATA) {
            if (IMPL_DATA.containsKey(interfaceClass))
                return (T) IMPL_DATA.get(interfaceClass);
            T _impl = BoxConfig.getSingleInstance(_implClassName, interfaceClass);
            IMPL_DATA.putIfAbsent(interfaceClass, _impl);
            return _impl;
        }
    }

    @Override
    public Collection getAllInterfaceImpl() {
        return Collections.unmodifiableCollection(IMPL_DATA.values());
    }

    /**
     * 加载指定属性对象中的配置信息
     *
     * @param props 属性对象
     */
    private void loadProperties(Properties props) {
        if (null == props || props.isEmpty())
            return;
        for (Map.Entry<Object, Object> _entry : props.entrySet()) {
            Object _keyObj = _entry.getKey();
            if (null == _keyObj)
                continue;
            String _key = _keyObj.toString().trim();
            if (0 == _key.length())
                continue;
            Object _valueObj = _entry.getValue();
            if (null == _valueObj) {
                this.DATA.put(_key, null);
                continue;
            }
            String _value = _valueObj.toString().trim();
            if (0 == _value.length())
                _value = null;
            this.DATA.put(_key, _value);
        }
    }

    /**
     * 初始化默认配置数据
     */
    private void initDefaultValues() {
        this.DATA.put(ILoggerFactory.class.getName(), ILoggerFactory.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ILoggerContext.class.getName(), ILoggerContext.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IFileSystem.class.getName(), IFileSystem.DEF_IMPL_CLASS_NAME);

        this.DATA.put(ICheckUtils.class.getName(), ICheckUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IStringUtils.class.getName(), IStringUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IMapUtils.class.getName(), IMapUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDateUtils.class.getName(), IDateUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IJsonUtils.class.getName(), IJsonUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IPropertiesUtils.class.getName(), IPropertiesUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IObjectUtils.class.getName(), IObjectUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IThreadUtils.class.getName(), IThreadUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IClassUtils.class.getName(), IClassUtils.DEF_IMPL_CLASS_NAME);

        this.DATA.put(IBizCodeManager.class.getName(), IBizCodeManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IBizExceptionFactory.class.getName(), IBizExceptionFactory.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ILocale.class.getName(), ILocale.DEF_IMPL_CLASS_NAME);

        this.DATA.put(IBoxContext.class.getName(), IBoxContext.DEF_IMPL_CLASS_NAME);

        this.DATA.put(ICacheManager.class.getName(), ICacheManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDsHelper.class.getName(), IDsHelper.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDsManager.class.getName(), IDsManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ISqlHelper.class.getName(), ISqlHelper.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ICmdFactory.class.getName(), ICmdFactory.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ISubjectContext.class.getName(), ISubjectContext.DEF_IMPL_CLASS_NAME);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleBoxConfig() {
        if (null != SimpleBoxConfigHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
        this.DATA = new HashMap<>(128);

        // 初始化默认配置数据
        initDefaultValues();
        // 加载外部配置数据，如果存在同名配置，以外部配置为准。
        Properties _props = new Properties();
        if (-1 != RESOURCE_DIR.indexOf(".jar")) {
            try (InputStream _inStream = SimpleBoxConfig.class.getResourceAsStream("/" + DEF_RESOURCE)) {
                _props.load(_inStream);
            } catch (IOException e) {
                throw new BoxRuntimeException(e);
            }
        } else {
            String _filePath = RESOURCE_DIR + DEF_RESOURCE;
            File _file = new File(_filePath);
            if (_file.exists()) {
                try (FileInputStream _inStream = new FileInputStream(_file)) {
                    _props.load(_inStream);
                } catch (IOException e) {
                    throw new BoxRuntimeException(e);
                }
            } else {
                throw new BoxRuntimeException("not found config file: " + _filePath);
            }
        }
        loadProperties(_props);
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleBoxConfigHolder {
        private static final SimpleBoxConfig _INSTANCE = new SimpleBoxConfig();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleBoxConfigHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleBoxConfig me() {
        return SimpleBoxConfigHolder._INSTANCE;
    }

}
