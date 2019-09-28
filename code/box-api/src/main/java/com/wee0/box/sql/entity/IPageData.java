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

package com.wee0.box.sql.entity;

import java.util.Collection;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 10:32
 * @Description 分页数据
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IPageData {

    /**
     * @return 页码
     */
    long getPageNum();

    /**
     * @return 每页数据量
     */
    long getPageSize();

    /**
     * @return 页数
     */
    long getPageCount();

    /**
     * @param <T> 数据类型
     * @return 分页数据
     */
    <T> Collection<T> getPageData();

    /**
     * @return 总数据量
     */
    long getDataCount();

}
