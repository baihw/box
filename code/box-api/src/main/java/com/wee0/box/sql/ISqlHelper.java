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

package com.wee0.box.sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:38
 * @Description SQL操作助手
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ISqlHelper {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.impl.SimpleSqlHelper";

    /**
     * 执行插入、修改、删除语句。
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 受影响的记录数
     */
    int update(String sql, Object[] sqlParams);

    /**
     * 批量执行插入、修改、删除语句。
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 每条语句受影响的记录数
     */
    int[] batch(String sql, Object[][] sqlParams);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 查询结果
     */
    Map<String, Object> queryMap(String sql, Object[] sqlParams);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 查询结果
     */
    List<Map<String, Object>> queryMapList(String sql, Object[] sqlParams);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @param type      对象类型
     * @return 查询结果
     */
    <T> T queryBean(String sql, Object[] sqlParams, Class<T> type);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @param type      对象类型
     * @return 查询结果
     */
    <T> List<T> queryBeanList(String sql, Object[] sqlParams, Class<T> type);

    /**
     * 执行查询，得到单行单列的数据
     *
     * @param sql          sql语句
     * @param sqlParams    sql语句参数集合
     * @param requiredType 返回值类型
     * @return 查询结果
     */
    <T> T queryScalar(String sql, Object[] sqlParams, Class<T> requiredType);

    /**
     * 执行查询，得到单行单列的数据
     *
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 查询结果
     */
    Long queryScalar(String sql, Object[] sqlParams);

    /**
     * 执行查询，得到单行单列的数据
     *
     * @param sql sql语句
     * @return 查询结果
     */
    Long queryScalar(String sql);

    // custom Connection support.

    /**
     * 执行插入、修改、删除语句。
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 受影响的记录数
     */
    int update(Connection conn, String sql, Object[] sqlParams);

    /**
     * 批量执行插入、修改、删除语句。
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 每条语句受影响的记录数
     */
    int[] batch(Connection conn, String sql, Object[][] sqlParams);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 查询结果
     */
    Map<String, Object> queryMap(Connection conn, String sql, Object[] sqlParams);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 查询结果
     */
    List<Map<String, Object>> queryMapList(Connection conn, String sql, Object[] sqlParams);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @param type      对象类型
     * @return 查询结果
     */
    <T> T queryBean(Connection conn, String sql, Object[] sqlParams, Class<T> type);

    /**
     * 执行查询语句，获取查询结果。
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @param type      对象类型
     * @return 查询结果
     */
    <T> List<T> queryBeanList(Connection conn, String sql, Object[] sqlParams, Class<T> type);

    /**
     * 执行查询，得到单行单列的数据
     *
     * @param conn         数据库连接
     * @param sql          sql语句
     * @param sqlParams    sql语句参数集合
     * @param requiredType 返回值类型
     * @return 查询结果
     */
    <T> T queryScalar(Connection conn, String sql, Object[] sqlParams, Class<T> requiredType);

    /**
     * 执行查询，得到单行单列的数据
     *
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param sqlParams sql语句参数集合
     * @return 查询结果
     */
    Long queryScalar(Connection conn, String sql, Object[] sqlParams);

    /**
     * 执行查询，得到单行单列的数据
     *
     * @param conn 数据库连接
     * @param sql  sql语句
     * @return 查询结果
     */
    Long queryScalar(Connection conn, String sql);

}
