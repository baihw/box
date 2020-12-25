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
import com.wee0.box.IBoxConfigObject;
import com.wee0.box.IBoxContext;
import com.wee0.box.cache.ICacheManager;
import com.wee0.box.code.IBizCodeManager;
import com.wee0.box.exception.IBizExceptionFactory;
import com.wee0.box.generator.IGenerator;
import com.wee0.box.i18n.ILocale;
import com.wee0.box.io.IFileSystem;
import com.wee0.box.log.ILoggerContext;
import com.wee0.box.log.ILoggerFactory;
import com.wee0.box.monitor.IMonitor;
import com.wee0.box.notify.sms.ISmsHelper;
import com.wee0.box.plugin.IPluginManager;
import com.wee0.box.sql.ISqlHelper;
import com.wee0.box.sql.dao.IPageHelper;
import com.wee0.box.sql.dialect.IDialectManager;
import com.wee0.box.sql.ds.IDsHelper;
import com.wee0.box.sql.ds.IDsManager;
import com.wee0.box.sql.template.ISqlTemplateHelper;
import com.wee0.box.sql.transaction.ITxManger;
import com.wee0.box.storage.IStorage;
import com.wee0.box.struct.ICmdFactory;
import com.wee0.box.subject.ISubjectContext;
import com.wee0.box.task.ITaskManager;
import com.wee0.box.template.ITemplateHandler;
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

    // 外部化资源文件根目录，默认为用户运行程序时所在的目录。外部化资源文件优先级高于jar包内资源文件。
    public static final String EXTERNAL_DIR = System.getProperty("user.dir").replaceAll(REG_FILE_SEPARATOR, "/");

    // 默认的资源文件
    public static final String DEF_RESOURCE = "config/box_config.properties";
    // 默认的包含隐私信息的资源文件
    public static final String DEF_PRIVATE_RESOURCE = "config/.private.properties";

    // 默认编码
    public static final String DEF_ENCODING = "UTF-8";

    // 全局类加载器
    private static final ClassLoader[] CLASS_LOADERS;

    static {
        // 使用IPv4网络
        System.setProperty("java.net.preferIPv4Stack", "true");
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
    private final Map<String, String> DATA = new LinkedHashMap<>(128);

    // 接口单例类实例缓存
    private final ConcurrentHashMap<Class, Object> IMPL_DATA = new ConcurrentHashMap<>(128);

    // 全局默认使用的编码
    private String encoding;
    // 框架组件定制对象
    private IBoxConfigObject configObject;

    @Override
    public IBoxConfigObject getConfigObject() {
        return this.configObject;
    }

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
    public Map<String, String> getByPrefix(String prefix) {
        if (null == prefix || 0 == (prefix = prefix.trim()).length())
            throw new IllegalArgumentException("prefix can't be empty!");
        final String _PREFIX = prefix;
        final int _PREFIX_LEN = prefix.length();
        final Map<String, String> _result = new LinkedHashMap<>(32);
        this.DATA.forEach((key, val) -> {
            if (key.startsWith(_PREFIX)) {
                _result.put(key.substring(_PREFIX_LEN), val);
            }
        });
        return _result;
    }

    @Override
    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public String getExternalDir() {
        return EXTERNAL_DIR;
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
        return _getResourceAsStream(resource);
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

    @Override
    public synchronized void loadData(Map<String, String> baseData) {
//        this.DATA.clear();
//        initDefaultValues();
        if (null != baseData && !baseData.isEmpty())
            this.DATA.putAll(baseData);

        // 加载外部配置数据，如果存在同名配置，以外部配置为准。
        Properties _props = new Properties();
        try (InputStream _inStream = _getResourceAsStream(DEF_RESOURCE);) {
            _props.load(_inStream);
        } catch (IOException e) {
            System.out.println("SimpleBoxConfig ignore file: " + e.getMessage());
//            throw new BoxRuntimeException(e);
        }
        // 加载包含隐私信息的配置数据，可以覆盖默认配置文件中加载的数据。
        try (InputStream _privateStream = _getResourceAsStream(DEF_PRIVATE_RESOURCE);) {
            _props.load(_privateStream);
        } catch (IOException e) {
            System.out.println("SimpleBoxConfig ignore private file: " + e.getMessage());
        }
//        if (-1 != RESOURCE_DIR.indexOf(".jar")) {
//            try (InputStream _inStream = SimpleBoxConfig.class.getResourceAsStream("/" + DEF_RESOURCE)) {
//                _props.load(_inStream);
//            } catch (IOException e) {
//                throw new BoxRuntimeException(e);
//            }
//        } else {
//            String _filePath = RESOURCE_DIR + DEF_RESOURCE;
//            File _file = new File(_filePath);
//            if (_file.exists()) {
//                try (FileInputStream _inStream = new FileInputStream(_file)) {
//                    _props.load(_inStream);
//                } catch (IOException e) {
//                    throw new BoxRuntimeException(e);
//                }
//            } else {
//                throw new BoxRuntimeException("not found config file: " + _filePath);
//            }
//        }
        loadProperties(_props);

        // 加载系统环境变量数据
        Map<String, String> _envData = System.getenv();
        if (null != _envData && !_envData.isEmpty()) {
            for (Map.Entry<String, String> _env : _envData.entrySet()) {
                String _key = _env.getKey();
                String _value = _env.getValue();
                if (null == _key || 0 == (_key = _key.trim()).length())
                    continue;
                if (null == _value || 0 == (_value = _value.trim()).length()) {
                    _value = null;
                }
                this.DATA.put(_key, _value);
            }
        }

        // 加载JVM环境变量数据
        loadProperties(System.getProperties());

        // 配置数据加载完成后需要执行的后置处理逻辑。
        _loadDataAfter();
    }

    private void _loadDataAfter() {
        // 缓存全局默认编码
        String _encoding = this.DATA.get(BoxConfigKeys.encoding);
        if (null == _encoding || 0 == (_encoding = _encoding.trim()).length())
            _encoding = DEF_ENCODING;
        this.encoding = _encoding;

        // 框架组件定制对象
        String _configObject = this.DATA.get(BoxConfigKeys.configObject);
        if (null == _configObject || 0 == (_configObject = _configObject.trim()).length())
            this.configObject = new SimpleBoxConfigObject();
        else
            this.configObject = BoxConfig.createInstance(_configObject, IBoxConfigObject.class);

        // 允许开发人员重设最终配置数据
        this.configObject.overrideBoxConfig(this.DATA);
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
        this.DATA.put(IByteUtils.class.getName(), IByteUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDigestUtils.class.getName(), IDigestUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IMapUtils.class.getName(), IMapUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDateUtils.class.getName(), IDateUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IJsonUtils.class.getName(), IJsonUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IPropertiesUtils.class.getName(), IPropertiesUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IObjectUtils.class.getName(), IObjectUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IThreadUtils.class.getName(), IThreadUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IClassUtils.class.getName(), IClassUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IValidateUtils.class.getName(), IValidateUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IPathUtils.class.getName(), IPathUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IIoUtils.class.getName(), IIoUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IHttpUtils.class.getName(), IHttpUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IZipUtils.class.getName(), IZipUtils.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IMonitor.class.getName(), IMonitor.DEF_IMPL_CLASS_NAME);

        this.DATA.put(IBizCodeManager.class.getName(), IBizCodeManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IBizExceptionFactory.class.getName(), IBizExceptionFactory.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ILocale.class.getName(), ILocale.DEF_IMPL_CLASS_NAME);

        this.DATA.put(IBoxContext.class.getName(), IBoxContext.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ITemplateHandler.class.getName(), ITemplateHandler.DEF_IMPL_CLASS_NAME);

        this.DATA.put(ICacheManager.class.getName(), ICacheManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDsHelper.class.getName(), IDsHelper.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDsManager.class.getName(), IDsManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ISqlHelper.class.getName(), ISqlHelper.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ITxManger.class.getName(), ITxManger.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IDialectManager.class.getName(), IDialectManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IPageHelper.class.getName(), IPageHelper.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ISqlTemplateHelper.class.getName(), ISqlTemplateHelper.DEF_IMPL_CLASS_NAME);

        this.DATA.put(IStorage.class.getName(), IStorage.DEF_IMPL_CLASS_NAME);

        this.DATA.put(ICmdFactory.class.getName(), ICmdFactory.DEF_IMPL_CLASS_NAME);
        this.DATA.put(ISubjectContext.class.getName(), ISubjectContext.DEF_IMPL_CLASS_NAME);

        this.DATA.put(ISmsHelper.class.getName(), ISmsHelper.DEF_IMPL_CLASS_NAME);

        this.DATA.put(ITaskManager.class.getName(), ITaskManager.DEF_IMPL_CLASS_NAME);
        this.DATA.put(IPluginManager.class.getName(), IPluginManager.DEF_IMPL_CLASS_NAME);

        this.DATA.put(IGenerator.class.getName(), IGenerator.DEF_IMPL_CLASS_NAME);
    }

    // 使用ClassLoader加载资源文件。
    private static InputStream _getResourceAsStreamByClassLoader(ClassLoader classLoader, String resource) {
        InputStream _inStream = classLoader.getResourceAsStream(resource);
        if (null == _inStream && '/' != resource.charAt(0)) {
            _inStream = classLoader.getResourceAsStream("/" + resource);
        }
        return _inStream;
    }

    // 获取资源文件流
    private static InputStream _getResourceAsStream(String resource) throws IOException {
        if (null == resource)
            throw new IOException("resource can not be null!");
        // 如果存在同名的外瓿配置文件，优先加载
        File _file = new File(EXTERNAL_DIR, resource);
        if (_file.exists())
            return new FileInputStream(_file);
        // 查找资源文件
        _file = new File(RESOURCE_DIR, resource);
        if (_file.exists())
            return new FileInputStream(_file);
        InputStream _inStream = _getResourceAsStreamByClassLoader(Thread.currentThread().getContextClassLoader(), resource);
        if (null != _inStream)
            return _inStream;
        for (ClassLoader _loader : CLASS_LOADERS) {
            _inStream = _getResourceAsStreamByClassLoader(_loader, resource);
            if (null != _inStream)
                return _inStream;
        }
        throw new IOException("not found resource: " + resource);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleBoxConfig() {
        if (null != SimpleBoxConfigHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        // 初始化默认配置数据
        initDefaultValues();

        // 是否手动加载配置数据，通常用于与每三方框架进行集成时，将第三方框架中的配置数据作为低优先级的配置项合并入配置数据中。
        String _configDataManualLoad = System.getProperty(KEY_MANUAL_LOAD_CONFIG_DATA);
        if ("true".equals(_configDataManualLoad)) return;

        // 加载配置数据
        loadData();
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
