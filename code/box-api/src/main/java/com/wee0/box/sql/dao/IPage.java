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

import com.sun.corba.se.spi.copyobject.CopyobjectDefaults;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 9:37
 * @Description 分页对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IPage {

    /**
     * 设置页码
     *
     * @param pageNum 页码
     */
    void setPageNum(long pageNum);

    /**
     * @return 页码
     */
    long getPageNum();

    /**
     * 设置每页数据量
     *
     * @param pageSize 每页数据量
     */
    void setPageSize(long pageSize);

    /**
     * @return 每页数据量
     */
    long getPageSize();

    /**
     * 设置总页数
     *
     * @param pageTotal 总页数
     */
    void setPageTotal(long pageTotal);

    /**
     * @return 总页数
     */
    long getPageTotal();

    /**
     * 设置总数据量
     *
     * @param dataTotal 总数据量
     */
    void setDataTotal(long dataTotal);

    /**
     * @return 总数据量
     */
    long getDataTotal();

    /**
     * 设置当前页数据
     *
     * @param pageData 当前页数据
     */
    void setPageData(Object pageData);

    /**
     * @return 当前页数据
     */
    Object getPageData();

}
