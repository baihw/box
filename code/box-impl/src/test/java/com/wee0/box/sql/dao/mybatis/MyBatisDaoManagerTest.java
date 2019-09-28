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
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.ds.DsManagerTest;
import com.wee0.box.testDaos.ISysUserDao;
import com.wee0.box.testEntities.SysUserEntity;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.io.DefaultVFS;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.Assert.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 22:59
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MyBatisDaoManagerTest {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(MyBatisDaoManagerTest.class);

    @BeforeClass
    public static void setup() {
        DsManagerTest.initTestDs();
    }

    //    @Test
    public void test01() {
        log.debug("myBatisHelper: {}", MyBatisDaoManager.me());
        SqlSessionFactory _sqlSessionFactory = MyBatisDaoManager.me().getSqlSessionFactory();
        Configuration _configuration = _sqlSessionFactory.getConfiguration();
        log.debug("configuration: {}", _configuration);
        for (String _mapName : _configuration.getResultMapNames()) {
            log.debug("mapName: {}", _mapName);
        }
        for (Object _obj : _configuration.getResultMaps()) {
            if (_obj instanceof ResultMap) {
                ResultMap _resultMap = (ResultMap) _obj;
                log.debug("map.id: {}", _resultMap.getId());
                log.debug("map.type: {}", _resultMap.getType());
                log.debug("map.class: {}", _resultMap.getClass());
            }
        }
        if (null != _configuration)
            return;

        Map<Method, MapperMethod> _methodCache = new HashMap<>();
        try {
            Class<?> _mapperInterface = ISysUserDao.class;
            Method _method;
//            _method = ISysUserDao.class.getMethod("findUserList", Map.class);
            _method = ISysUserDao.class.getMethod("count");
            String _statementName = _mapperInterface.getName() + "." + _method.getName();
            log.debug("method name: {}", _statementName);
            log.debug("has: {}, isDefault: {}", _configuration.hasStatement(_statementName), _method.isDefault());
            if (!_configuration.hasStatement(_statementName)) {
                SqlSourceBuilder _sqlSourceBuilder = new SqlSourceBuilder(_configuration);

                String _sql = "select count(1) from sys_user";
                Object _parameterObject = null;
                Class<?> _parameterType = null == _parameterObject ? Object.class : _parameterObject.getClass();
                DynamicContext _context = new DynamicContext(_configuration, _parameterObject);
                SqlSource _sqlSource = _sqlSourceBuilder.parse(_sql, _parameterType, _context.getBindings());
                BoundSql _boundSql = _sqlSource.getBoundSql(_parameterObject);
                for (Map.Entry<String, Object> entry : _context.getBindings().entrySet()) {
                    _boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
                }
                log.debug("boundSql: {}", _boundSql);

                MappedStatement.Builder _builder = new MappedStatement.Builder(_configuration, _statementName, _sqlSource, SqlCommandType.SELECT);
                _builder.statementType(StatementType.PREPARED);
                List<ResultMap> _resultMaps = new ArrayList<>(1);
                ResultMap _inlineResultMap = new ResultMap.Builder(
                        _configuration,
                        _statementName + "-Inline",
                        _method.getReturnType(),
                        new ArrayList<>(),
                        null).build();
                _resultMaps.add(_inlineResultMap);
                _builder.resultMaps(_resultMaps);
                MappedStatement _mappedStatement = _builder.build();
                _configuration.addMappedStatement(_mappedStatement);
            }

            MapperMethod _mapperMethod = _methodCache.computeIfAbsent(_method, k -> new MapperMethod(_mapperInterface, _method, _configuration));
            log.debug("mapperMethod: {}", _mapperMethod);

            Object _obj = _mapperMethod.execute(_sqlSessionFactory.openSession(), new Object[]{null});
            log.debug("obj: {}", _obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        Collection<MappedStatement> _statements = _sqlSessionFactory.getConfiguration().getMappedStatements();
//        for (Object _statement : _statements) {
//            log.debug("statement: {}", _statement);
//            if (_statement instanceof MappedStatement) {
//                MappedStatement _mappedStatement = (MappedStatement) _statement;
//                log.debug("type: {}, id: {}", _mappedStatement.getSqlCommandType(), _mappedStatement.getId());
//                log.debug("sqlSource: {}, statementType: {}", _mappedStatement.getSqlSource(), _mappedStatement.getStatementType());
//                log.debug("boundSql: {}", _mappedStatement.getBoundSql(null).getSql());
//            }
//        }

    }

    @Test
    public void test02() {
        MyBatisDaoManager.me().registerDao(ISysUserDao.class);
        ISysUserDao _sysUserDao = MyBatisDaoManager.me().getDao(ISysUserDao.class);
        log.debug("sysUserDao: {}", _sysUserDao);
        log.debug("sysUserDao.countAll: {}", _sysUserDao.countAll());
        log.debug("sysUserDao.queryAllByPage: {}", _sysUserDao.queryAllByPage(3, 2));
        log.debug("sysUserDao.existsById[43]: {}", _sysUserDao.existsById(43)); //47
        log.debug("sysUserDao.existsById[99]: {}", _sysUserDao.existsById(99)); //47
//        log.debug("sysUserDao.deleteAll: {}", _sysUserDao.deleteAll());
        List<Integer> _ids = new ArrayList<>(2);
        _ids.add(43);
        _ids.add(47);
        log.debug("sysUserDao.queryByIds[43, 47]: {}", _sysUserDao.queryByIds(_ids));
        SysUserEntity _entity = _sysUserDao.queryById(47);
        log.debug("sysUserDao.queryById[47]: {}", _entity);
        _entity.setWechat("test_weChat");
        log.debug("sysUserDao.updateEntity[47]: {}", _sysUserDao.updateEntity(_entity));
        log.debug("sysUserDao.queryById[47]: {}", _entity);
        _entity.setId("99");
        _entity.setWechat("test_weChat_99");
        log.debug("sysUserDao.insertEntity[99]: {}", _sysUserDao.insertEntity(_entity));
        log.debug("sysUserDao.queryById[99]: {}", _sysUserDao.queryById(99));
//        log.debug("sysUserDao.deleteById[99]: {}", _sysUserDao.deleteById(99));
        _ids.clear();
        _ids.add(99);
        log.debug("sysUserDao.deleteByIds[99]: {}", _sysUserDao.deleteByIds(_ids));
        log.debug("sysUserDao.queryById[99]: {}", _sysUserDao.queryById(99));
//        log.debug("sysUserDao.queryById[47]: {}", _sysUserDao.queryById(47));


    }

//    void _test() {
//        try (SqlSession _session = MyBatisHelper.me().getSqlSessionFactory().openSession();) {
//            ISysUserDao _dao = _session.getMapper(ISysUserDao.class);
//            log.debug("dao: {}", _dao);
//            List<Map<String, Object>> _userList = _dao.findUserList(null);
//            log.debug("_userList: {}", _userList);
//        }
//    }

}