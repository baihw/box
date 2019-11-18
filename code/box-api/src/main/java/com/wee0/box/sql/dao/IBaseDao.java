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

package com.wee0.box.sql.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/7 22:58
 * @Description Dao对象基础方法
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IBaseDao<T, ID extends Serializable> extends IDao<T, ID> {

    /**
     * 插入数据
     *
     * @param entity 实体对象
     * @return 受影响的记录数
     */
    <S extends T> int insertEntity(S entity);

    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 受影响的记录数
     */
    <S extends T> int updateEntity(S entity);

    /**
     * 删除指定标识数据
     *
     * @param id 唯一标识
     * @return 受影响的记录数
     */
    int deleteById(ID id);

    /**
     * 删除指定标识数据
     *
     * @param ids 唯一标识集合
     * @return 受影响的记录数
     */
    int deleteByIds(List<? extends ID> ids);

    /**
     * 删除所有数据
     *
     * @return 受影响的记录数
     */
    int deleteAll();

    /**
     * 统计总数量
     *
     * @return 总数量
     */
    long countAll();

    /**
     * 是否存在指定标识数据
     *
     * @param id 唯一标识
     * @return true / false
     */
    Boolean existsById(ID id);

    /**
     * 查询指定标识数据
     *
     * @param id 唯一标识
     * @return 数据
     */
    T queryById(ID id);

    /**
     * 查询指定标识数据
     *
     * @param ids 唯一标识集合
     * @return 数据
     */
    List<T> queryByIds(List<? extends ID> ids);

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    List<T> queryAll();

    /**
     * 查询分页数据
     *
     * @param params 参数集合
     * @return 分页数据
     */
    List<T> queryAllByPage(Map<String, Object> params);

}
