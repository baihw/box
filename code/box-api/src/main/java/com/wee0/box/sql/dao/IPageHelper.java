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

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 9:52
 * @Description 分页助手
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IPageHelper {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.dao.mybatis.MybatisPageHelper";

    /**
     * 创建一个分页请求参数集合对象
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页请求参数集合对象
     */
    Map<String, Object> createPageParams(long pageNum, long pageSize);

    /**
     * 从包含请求参数的集合对象中解析出分页对象
     *
     * @param pageParams 分页请求参数集合对象
     * @return 分页对象
     */
    IPage parseMap(Map<String, Object> pageParams);

    /**
     * @return 当前使用的页码参数名
     */
    String getPageNumKey();

    /**
     * @return 当前使用的每页数据量参数名
     */
    String getPageSizeKey();

    /**
     * @return 当前使用的分页对象参数名
     */
    String getPageKey();

}
