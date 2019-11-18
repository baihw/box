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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/22 7:12
 * @Description 抽象方言基类
 * <pre>
 * 补充说明
 * </pre>
 **/
abstract class AbstractDialect implements IDialect {

    @Override
    public String insertEntity(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(192);
        StringBuilder _valuesBuilder = new StringBuilder(128);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("<script>");
        _builder.append("insert into ").append(_tableInfo.getTableName());
        _builder.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        _tableInfo.visitFieldColumns((field, column) -> {
            final String _fieldName = field.getName();
//            _builder.append("<if test=\"").append(_fieldName).append("!=null and ").append(_fieldName).append("!=''\">").append(column).append(",</if>");
            _builder.append("<if test=\"").append(_fieldName).append("!=null\">").append(column).append(",</if>");
            _valuesBuilder.append("<if test=\"").append(_fieldName).append("!=null\">").append("#{").append(_fieldName).append("},</if>");
        });
        _builder.append("</trim>");
        _builder.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        _builder.append(_valuesBuilder);
        _builder.append("</trim>");
        _builder.append("</script>");
        return _builder.toString();
    }

    @Override
    public String updateEntity(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(192);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("<script>");
        _builder.append("update ").append(_tableInfo.getTableName());
        _builder.append("<trim prefix=\"set\" suffixOverrides=\",\">");
        _tableInfo.visitFieldColumns((field, column) -> {
            if (column.equals(_tableInfo.getIdColumn()))
                return;
            final String _fieldName = field.getName();
//            _builder.append("<if test=\"").append(_fieldName).append("!=null and ").append(_fieldName).append("!=''\">");
            _builder.append("<if test=\"").append(_fieldName).append("!=null \">");
            _builder.append(column).append("=").append("#{").append(_fieldName).append("},");
            _builder.append("</if>");
        });
        _builder.append("</trim>");
        _builder.append("where ").append(_tableInfo.getIdColumn()).append("=#{").append(_tableInfo.getIdField().getName()).append("}");
        _builder.append("</script>");
        return _builder.toString();
    }

    @Override
    public String deleteById(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(128);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("delete from ").append(_tableInfo.getTableName());
        _builder.append(" where ").append(_tableInfo.getIdColumn()).append(" = #{id}");
        return _builder.toString();
    }

    @Override
    public String deleteByIds(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(128);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("<script>");
        _builder.append("delete from ").append(_tableInfo.getTableName()).append(" where 1>2 or ");
        _builder.append(_tableInfo.getIdColumn()).append(" in ");
        _builder.append("<foreach collection=\"list\"  item=\"item\" open=\"(\" separator=\",\" close=\")\"  >");
        _builder.append("#{item}");
        _builder.append("</foreach>");
        _builder.append("</script>");
        return _builder.toString();
    }

    @Override
    public String deleteAll(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(32);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("delete * from ").append(_tableInfo.getTableName());
        return _builder.toString();
    }

    @Override
    public String countAll(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(32);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("select count(1) from ").append(_tableInfo.getTableName());
        return _builder.toString();
    }

    @Override
    public String existsById(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(64);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("select 1 from ").append(_tableInfo.getTableName());
//        _builder.append(" where ").append(_tableInfo.getIdColumn()).append(" = ").append("#{").append(_tableInfo.getIdField().getName()).append("}");
        _builder.append(" where ").append(_tableInfo.getIdColumn()).append(" = #{0}");
        return _builder.toString();
    }

    @Override
    public String queryById(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(64);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("select * from ").append(_tableInfo.getTableName());
        _builder.append(" where ").append(_tableInfo.getIdColumn()).append("=#{id}");
        return _builder.toString();
    }

    @Override
    public String queryByIds(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(128);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("<script>");
        _builder.append("select * from ").append(_tableInfo.getTableName()).append(" where 1=1 and ");
        _builder.append(_tableInfo.getIdColumn()).append(" in ");
        _builder.append("<foreach collection=\"list\"  item=\"item\" open=\"(\" separator=\",\" close=\")\"  >");
        _builder.append("#{item}");
        _builder.append("</foreach>");
        _builder.append("</script>");
        return _builder.toString();
    }

    @Override
    public String queryAll(Class<?> entityClass) {
        StringBuilder _builder = new StringBuilder(32);
        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
        _builder.append("select * from ").append(_tableInfo.getTableName());
        return _builder.toString();
    }

    @Override
    public String queryAllByPage(Class<?> entityClass) {
        // 默认不处理分页逻辑，由分页插件来处理。
        return queryAll(entityClass);
    }
}
