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

package com.wee0.box.sql.dao.mybatis;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.io.FileSystem;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.spi.impl.SimpleSpiManager;
import com.wee0.box.sql.dao.IBaseDao;
import com.wee0.box.sql.dao.IDao;
import com.wee0.box.sql.dao.IDaoHandler;
import com.wee0.box.sql.dao.IDaoManager;
import com.wee0.box.sql.ds.DatabaseId;
import com.wee0.box.sql.ds.DsManager;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.javassist.tools.reflect.Reflection;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/7 22:56
 * @Description 基于MyBatis的Dao对象处理实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MyBatisDaoManager implements IDaoManager {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(MyBatisDaoManager.class);

    // 默认的配置文件
    private static final String DEF_CONFIG_FILE = "mybatis/mybatis-config.xml";
    // 默认的Mapper文件目录
    private static final String DEF_MAPPER_DIR = "mybatis/mapper/";
    // 默认的Mapper文件后缀
    private static final String DEF_MAPPER_FILE_SUFFIX = "Mapper.xml";
    // 超类
    private static final Class SUPER_TYPE = IDao.class;

    // 数据容器
    private final Map<Class<? extends IBaseDao>, IBaseDao> DATA = new ConcurrentHashMap<>(256);

    // 配置对象
    private final Configuration configuration;
    // 会话工厂对象
    private final SqlSessionFactory sqlSessionFactory;
    // 会话管理对象
    private final SqlSessionManager sqlSessionManager;

    private final List<IDaoHandler> daoHandlers;


    @Override
    public <T> T getDao(Class<T> interfaceClass) {
        return this.sqlSessionManager.getMapper(interfaceClass);
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void registerDao(Class clazz) {
        CheckUtils.checkNotNull(clazz, "clazz cannot be null!");
        CheckUtils.checkArgument(!clazz.isInterface(), clazz + " must be a interface!");
        CheckUtils.checkArgument(!SUPER_TYPE.isAssignableFrom(clazz), clazz + " must be extends IDao!");

        LanguageDriver _languageDriver = configuration.getLanguageDriver(XMLLanguageDriver.class);
        log.trace("languageDriver: {}", _languageDriver);
        log.trace("registerDao: {}", clazz);

        final String _namespace = StringUtils.endsWithChar(clazz.getName(), '.');
        final Class<?> _entityClass = getEntityType(clazz);
        Method[] _methods = clazz.getMethods();
        for (Method _method : _methods) {
            if (clazz == _method.getDeclaringClass())
                continue;
            String _methodId = _namespace.concat(_method.getName());
            if (configuration.hasStatement(_methodId))
                continue;

            log.debug("generate: {}", _methodId);
//            String _sqlScript = "<script></script>";
            String _sqlScript = getSqlScript(DsManager.impl().getDefaultDatabaseId(), _method, _entityClass);
            if (null == _sqlScript)
                continue;
            SqlSource _sqlSource = _languageDriver.createSqlSource(configuration, _sqlScript, null);

            // 结果映射
            Class<?> _resultType = getResultType(_method, clazz);
            String _resultMapId = _resultType.getName(); // _resultType.getName() + "-Inline";
            ResultMap _resultMap = null;
            if (configuration.hasResultMap(_resultMapId)) {
                _resultMap = configuration.getResultMap(_resultMapId);
            } else {
                _resultMap = new ResultMap.Builder(
                        configuration,
                        _resultMapId,
                        _resultType,
                        new ArrayList<ResultMapping>(0)).build();
                configuration.addResultMap(_resultMap);
            }

            SqlCommandType _sqlCommandType = getSqlCommandType(_method);
            MappedStatement.Builder _builder = new MappedStatement.Builder(configuration, _methodId, _sqlSource, _sqlCommandType);
            _builder.statementType(StatementType.PREPARED);
            _builder.resultMaps(Collections.singletonList(_resultMap));
            MappedStatement _mappedStatement = _builder.build();
            configuration.addMappedStatement(_mappedStatement);
        }

    }

    // 获取Sql语句类型
    SqlCommandType getSqlCommandType(Method method) {
        final String _methodName = method.getName();
        if (_methodName.startsWith("insert"))
            return SqlCommandType.INSERT;
        if (_methodName.startsWith("update"))
            return SqlCommandType.UPDATE;
        if (_methodName.startsWith("delete"))
            return SqlCommandType.DELETE;
        return SqlCommandType.SELECT;
    }


    // 获取指定方法对象的sql语句
    String getSqlScript(DatabaseId databaseId, Method method, Class<?> entityClass) {
        String _sql = null;
        for (IDaoHandler _daoHandler : daoHandlers) {
            _sql = _daoHandler.generateSql(databaseId, method, entityClass);
            if (null != _sql)
                break;
        }
        if (null == _sql || 0 == (_sql = _sql.trim()).length())
            throw new BoxRuntimeException("IDaoHandler.generateSql must return at least one non-empty!");
        return _sql;
    }

    // 获取实体对象类型
    static Class<?> getEntityType(Class daoClass) {
        Type _type = daoClass.getGenericInterfaces()[0];
        if (_type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) _type).getActualTypeArguments()[0];
        } else {
            return _type.getClass();
        }
    }

    // 获取返回值类型
    static Class<?> getResultType(Method method, Class<?> clazz) {
        Class<?> _result = method.getReturnType();
        Type _returnType = TypeParameterResolver.resolveReturnType(method, clazz);
        _result = typeToClass(_returnType);
        return _result;
    }

//    // 获取返回值类型
//    static Class<?> getResultType(Method method, Class<?> clazz) {
//        if (Collection.class.isAssignableFrom(clazz)) {
//            Type _type = clazz.getGenericSuperclass();
//            if(_type instanceof ParameterizedType){
//                return (Class<?>) ((ParameterizedType) _type).getActualTypeArguments()[0];
//            }else{
//                return _type.getClass();
//            }
//        }
//        return clazz;
//    }

    private static Class<?> typeToClass(Type src) {
        Class<?> result = null;
        if (src instanceof Class) {
            result = (Class<?>) src;
        } else if (src instanceof ParameterizedType) {
//            result = (Class<?>) ((ParameterizedType) src).getRawType();
            result = (Class<?>) ((ParameterizedType) src).getActualTypeArguments()[0];
        } else if (src instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) src).getGenericComponentType();
            if (componentType instanceof Class) {
                result = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance(componentClass, 0).getClass();
            }
        }
        if (result == null) {
            result = Object.class;
        }
        return result;
    }

    // 初始化配置对象
    private void initConfiguration(Configuration configuration) {
        // 设置数据库标识
        configuration.setDatabaseId(DsManager.impl().getDefaultDatabaseId().name());

//        configuration.setVfsImpl(DefaultVFS.class);
//        configuration.setObjectFactory();
//        configuration.setObjectWrapperFactory();

        // 别名注册
        TypeAliasRegistry _typeAliasRegistry = configuration.getTypeAliasRegistry();
        Class<?> _typeAliasesSuperType = null;
        String[] _packages = new String[]{};
        for (String _package : _packages) {
            _typeAliasRegistry.registerAliases(_package, null == _typeAliasesSuperType ? Object.class : _typeAliasesSuperType);
        }
        Class<?>[] _typeAliases = new Class[]{};
        for (Class<?> _typeAlias : _typeAliases) {
            _typeAliasRegistry.registerAlias(_typeAlias);
        }

        // 类型注册
        TypeHandlerRegistry _typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        for (String _package : _packages) {
            _typeHandlerRegistry.register(_package);
        }

        // 插件注册
        Interceptor[] _interceptors = new Interceptor[]{};
        for (Interceptor _interceptor : _interceptors) {
            configuration.addInterceptor(_interceptor);
        }
    }

    // 加载映射文件
    private void loadMappers() {
        // 设置环境
        TransactionFactory _transactionFactory = new JdbcTransactionFactory();
        Environment _environment = new Environment(getClass().getSimpleName(), _transactionFactory, DsManager.impl().getDefaultDataSource());
        configuration.setEnvironment(_environment);

        // 解析mapper文件
        List<String> _resources = null;
        try {
            _resources = FileSystem.impl().list(DEF_MAPPER_DIR, (name) -> name.endsWith(DEF_MAPPER_FILE_SUFFIX));
        } catch (IOException e) {
            log.warn("Failed to access mapper directory!", e);
            return;
        }
        for (String _resource : _resources) {
            try (InputStream _inStream = Resources.getResourceAsStream(_resource);) {
                XMLMapperBuilder _mapperBuilder = new XMLMapperBuilder(_inStream, this.configuration, _resource, this.configuration.getSqlFragments());
                _mapperBuilder.parse();
            } catch (Exception e) {
                throw new BoxRuntimeException("Failed to parse mapping resource: " + _resource, e);
            } finally {
                ErrorContext.instance().reset();
            }
            log.debug("Parsed mapper file: {}", _resource);
        }
    }

//    private void _buildMappedStatement(Method method, Class<?> _entityClass){
//        //生成语句
//        SqlSourceBuilder _sqlSourceBuilder = new SqlSourceBuilder(configuration);
//        String _sql = getSql(DsManager.impl().getDefaultDatabaseId(), method, _entityClass);
//
//        // 参数映射
//        Object _parameterObject = null; // _method.getGenericParameterTypes();
//        Class<?> _parameterType = null == _parameterObject ? Object.class : _parameterObject.getClass();
//        DynamicContext _context = new DynamicContext(configuration, _parameterObject);
//        SqlSource _sqlSource = _sqlSourceBuilder.parse(_sql, _parameterType, _context.getBindings());
//        BoundSql _boundSql = _sqlSource.getBoundSql(_parameterObject);
//        for (Map.Entry<String, Object> entry : _context.getBindings().entrySet()) {
//            _boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
//        }
//
//        // 结果映射
//        Class<?> _resultType = getResultType(method, clazz);
//        String _resultMapId = _resultType.getName();
//        if (configuration.hasResultMap(_resultMapId))
//            continue;
//        ResultMap _resultMap = createDefaultResultMap(_resultType, configuration);
//        configuration.addResultMap(_resultMap);
//
//        MappedStatement.Builder _builder = new MappedStatement.Builder(configuration, _methodId, _sqlSource, SqlCommandType.SELECT);
//        _builder.statementType(StatementType.PREPARED);
//        _builder.resultMaps(Collections.singletonList(_resultMap));
//        MappedStatement _mappedStatement = _builder.build();
//        configuration.addMappedStatement(_mappedStatement);
//    }

    /**
     * 创建一个默认的配置对象
     *
     * @return 配置对象
     */
    private static Configuration createDefaultConfiguration() {
        Configuration _configuration = new Configuration();
        // 全局映射器缓存
        _configuration.setCacheEnabled(false);
        // 查询时,关闭关联对象加载以提高性能
        _configuration.setLazyLoadingEnabled(false);
        // 设置关联对象加载的形态,此处为按需加载字段(加载字段由SQL指定),不会加载关联表的所有字段,以提高性能
        _configuration.setAggressiveLazyLoading(false);
        // 对于未知的SQL查询,允许返回不同的结果集以达到通用的效果
        _configuration.setMultipleResultSetsEnabled(true);
        // 允许使用列标签代替列名
        _configuration.setUseColumnLabel(true);
        // 允许使用自定义的主键值(比如由程序生成的UUID 32位编码作为键值), 数据表的pk生成策略将被覆盖
        _configuration.setUseGeneratedKeys(false);
        // 给予被嵌套的resultMap以字段-属性的映射支持
        _configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
        // 对于批量更新操作缓存SQL以提高性能
        _configuration.setDefaultExecutorType(ExecutorType.REUSE);
        // 下划线命名转驼峰命名
        _configuration.setMapUnderscoreToCamelCase(true);
        // 默认数据库响应超时时间
        _configuration.setDefaultStatementTimeout(120);
        _configuration.setLocalCacheScope(LocalCacheScope.SESSION);
        _configuration.setJdbcTypeForNull(JdbcType.NULL);
        _configuration.setLogImpl(Slf4jImpl.class);
//        _configuration.setLogImpl(StdOutImpl.class);
        return _configuration;
    }


    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private MyBatisDaoManager() {
        if (null != MyBatisDaoManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        this.daoHandlers = SimpleSpiManager.getImplLIst(IDaoHandler.class);
        this.daoHandlers.add(new DefaultDaoHandler());

        XMLConfigBuilder _xmlConfigBuilder = null;
        try (InputStream _inStream = Resources.getResourceAsStream(DEF_CONFIG_FILE);) {
            _xmlConfigBuilder = new XMLConfigBuilder(_inStream);
//            this.configuration = _xmlConfigBuilder.getConfiguration();
        } catch (IOException e) {
//            this.configuration = createDefaultConfiguration();
        }
        this.configuration = null == _xmlConfigBuilder ? createDefaultConfiguration() : _xmlConfigBuilder.getConfiguration();

        // 初始化
        initConfiguration(this.configuration);

        // 解析xml配置
        if (null != _xmlConfigBuilder) {
            try {
                _xmlConfigBuilder.parse();
                log.debug("Parsed config file: {}", DEF_CONFIG_FILE);
            } catch (Exception ex) {
                throw new BoxRuntimeException("Failed to parse config file: " + DEF_CONFIG_FILE, ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        // 加载映射文件
        loadMappers();

        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(this.configuration);
        log.debug("sqlSessionFactory: {}", this.sqlSessionFactory);
        this.sqlSessionManager = SqlSessionManager.newInstance(this.sqlSessionFactory);
        log.debug("sqlSessionManager: {}", this.sqlSessionManager);
    }

    // 当前对象唯一实例持有者。
    private static final class MyBatisDaoManagerHolder {
        private static final MyBatisDaoManager _INSTANCE = new MyBatisDaoManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return MyBatisDaoManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static MyBatisDaoManager me() {
        return MyBatisDaoManagerHolder._INSTANCE;
    }


}
