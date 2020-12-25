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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.dao.IPage;
import com.wee0.box.sql.dao.PageHelper;
import com.wee0.box.sql.dialect.DialectManager;
import com.wee0.box.util.shortcut.CheckUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 8:02
 * @Description 自定义分页插件
 * <pre>
 * 补充说明
 * </pre>
 **/
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),}
)
public class MybatisPageInterceptor implements Interceptor {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(MybatisPageInterceptor.class);

    // 默认的分页方法名后缀
    static final String DEF_PAGE_METHOD_SUFFIX = "Page";

    // 需要分页的方法名称后缀
    private String pageMethodSuffix;

    public MybatisPageInterceptor() {
        // Spring bean 方式配置时，如果没有配置属性就不会执行 setProperties 方法进行初始化，这里先初始化好默认值。
        this.pageMethodSuffix = DEF_PAGE_METHOD_SUFFIX;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] _args = invocation.getArgs();
        MappedStatement _mappedStatement = (MappedStatement) _args[0];
        String _msId = _mappedStatement.getId();
        if (!_msId.endsWith(this.pageMethodSuffix))
            return invocation.proceed();

        Object _parameter = _args[1];
        RowBounds _rowBounds = (RowBounds) _args[2];
        ResultHandler _resultHandler = (ResultHandler) _args[3];
        Executor _executor = (Executor) invocation.getTarget();
        CacheKey _cacheKey;
        BoundSql _boundSql;
        if (_args.length == 4) {
            _boundSql = _mappedStatement.getBoundSql(_parameter);
            _cacheKey = _executor.createCacheKey(_mappedStatement, _parameter, _rowBounds, _boundSql);
        } else {
            _boundSql = (BoundSql) _args[5];
            _cacheKey = (CacheKey) _args[4];
        }

//        log.trace("_mappedStatement:{}", _mappedStatement);
//        log.trace("_mappedStatementId:{}" + _msId);
//        log.trace("_parameter:{}", _parameter);
//        log.trace("_rowBounds:{}", _rowBounds);
//        log.trace("_resultHandler:{}", _resultHandler);
//        log.trace("_boundSql:{}" + _boundSql);
//        log.trace("_boundSql.sql:{}" + _boundSql.getSql());
//        log.trace("_cacheKey:{}" + _cacheKey);

        if (null == _parameter) {
            // 没有参数，不处理。
            log.debug("parameterObject is null, skip...");
            return invocation.proceed();
        }
        if (!(_parameter instanceof Map) || _parameter instanceof MapperMethod.ParamMap) {
            // 不是原生的map，不处理。
            log.debug("parameterObject is not a native map, skip...");
            return invocation.proceed();
        }

        Map<String, Object> _paramsMap = (Map<String, Object>) _parameter;
        IPage _page = PageHelper.impl().parseMap(_paramsMap);
        log.debug("_page: {}", _page);
        if (0 == _page.getPageSize()) {
            // 如果pageSize为0，则不分页。
            log.debug("pageSize is zero, skip...");
            return invocation.proceed();
        }

        Field _additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
        _additionalParametersField.setAccessible(true);
        Map<String, Object> _additionalParameters = (Map<String, Object>) _additionalParametersField.get(_boundSql);
        log.trace("_additionalParameters:{}", _additionalParameters);

//        List<ParameterMapping> _paraList = _boundSql.getParameterMappings();
//        log.debug("_paraList:{}", _paraList);
//        _paraList.forEach((p) -> {
//            log.debug("k:{}, v:{}, p:{}", p.getProperty(), _boundSql.getAdditionalParameter(p.getProperty()), p);
//        });

        // 统计记录数
        Long _countResult = getCountResult(_mappedStatement, _parameter, _additionalParameters, _boundSql, _executor, _resultHandler);
        _page.setDataTotal(_countResult);
        if (0L == _countResult) {
            return Collections.EMPTY_LIST;
        }

        String _pageSql = DialectManager.impl().getDialect().getPageSql(_boundSql.getSql(), _page.getPageNum(), _page.getPageSize());
        BoundSql _pageBoundSql = new BoundSql(_mappedStatement.getConfiguration(), _pageSql, _boundSql.getParameterMappings(), _parameter);
        _additionalParameters.forEach((_key, _value) -> {
            _pageBoundSql.setAdditionalParameter(_key, _value);
        });
        List _pageResultList = _executor.query(_mappedStatement, _parameter, RowBounds.DEFAULT, _resultHandler, _cacheKey, _pageBoundSql);

        _page.setPageData(_pageResultList);
        long _pageTotal = _page.getDataTotal() / _page.getPageSize() + ((_page.getDataTotal() % _page.getPageSize()) > 0 ? 1 : 0);
        _page.setPageTotal(_pageTotal);
        return _pageResultList;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 读取设置的分页方法后缀
        String _pageMethodSuffix = CheckUtils.checkTrimEmpty(properties.getProperty("pageMethodSuffix"), null);
        if (null != _pageMethodSuffix) {
            this.pageMethodSuffix = _pageMethodSuffix;
        }
    }

    private static final List<ResultMapping> _EMPTY_RESULTMAPPING = new ArrayList<>(0);

    // 获取查询语句未分页情况下的总数据量
    private Long getCountResult(MappedStatement statement, Object parameter, Map<String, Object> additionalParameters, BoundSql boundSql, Executor executor, ResultHandler resultHandler) throws SQLException {
        String _countId = statement.getId() + "_COUNT";
        Configuration _configuration = statement.getConfiguration();
        MappedStatement _countMS;
        if (_configuration.hasStatement(_countId)) {
            _countMS = _configuration.getMappedStatement(_countId);
        } else {
            _countMS = createMappedStatement(statement, _countId);
            synchronized (_configuration) {
                if (!_configuration.hasStatement(_countId))
                    _configuration.addMappedStatement(_countMS);
            }
        }
        CacheKey _countKey = executor.createCacheKey(_countMS, parameter, RowBounds.DEFAULT, boundSql);
        String _countSql = DialectManager.impl().getDialect().getCountSql(boundSql.getSql());
        BoundSql _countBoundSql = new BoundSql(statement.getConfiguration(), _countSql, boundSql.getParameterMappings(), parameter);
        additionalParameters.forEach((_key, _value) -> {
            _countBoundSql.setAdditionalParameter(_key, _value);
        });
        Object _countResultList = executor.query(_countMS, parameter, RowBounds.DEFAULT, resultHandler, _countKey, _countBoundSql);
        Long _countResult = (Long) ((List) _countResultList).get(0);
        return _countResult;
    }

    // 创建一个指定标识的MappedStatement
    private MappedStatement createMappedStatement(MappedStatement statement, String id) {
        MappedStatement.Builder _builder = new MappedStatement.Builder(statement.getConfiguration(), id, statement.getSqlSource(), statement.getSqlCommandType());
        _builder.resource(statement.getResource());
        _builder.fetchSize(statement.getFetchSize());
        _builder.statementType(statement.getStatementType());
        _builder.keyGenerator(statement.getKeyGenerator());
        if (null != statement.getKeyProperties() && 0 != statement.getKeyProperties().length) {
            StringBuilder _keyProperties = new StringBuilder();
            for (String _keyProperty : statement.getKeyProperties()) {
                _keyProperties.append(_keyProperty).append(',');
            }
            _keyProperties.deleteCharAt(_keyProperties.length() - 1);
            _builder.keyProperty(_keyProperties.toString());
        }
        _builder.timeout(statement.getTimeout());
        _builder.parameterMap(statement.getParameterMap());
        List<ResultMap> _resultMaps = new ArrayList<>(1);
        ResultMap _resultMap = new ResultMap.Builder(statement.getConfiguration(), statement.getId(), Long.class, _EMPTY_RESULTMAPPING).build();
        _resultMaps.add(_resultMap);
        _builder.resultMaps(_resultMaps);
        _builder.resultSetType(statement.getResultSetType());
        _builder.cache(statement.getCache());
        _builder.flushCacheRequired(statement.isFlushCacheRequired());
        _builder.useCache(statement.isUseCache());
        return _builder.build();
    }

}
