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

import com.wee0.box.sql.dao.IPage;
import com.wee0.box.util.shortcut.ObjectUtils;

import java.util.Collection;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 10:22
 * @Description 一个简单的分页实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimplePage implements IPage {

    // 页码
    private long pageNum;
    // 每页大小
    private long pageSize;
    // 页数
    private long pageTotal;
    // 数据量
    private long dataTotal;
    // 当前页数据
    private Object pageData;

    @Override
    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public long getPageNum() {
        return this.pageNum;
    }

    @Override
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public long getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }

    @Override
    public long getPageTotal() {
        return this.pageTotal;
    }

    @Override
    public void setDataTotal(long dataTotal) {
        this.dataTotal = dataTotal;
    }

    @Override
    public long getDataTotal() {
        return this.dataTotal;
    }

    @Override
    public void setPageData(Object pageData) {
        this.pageData = pageData;
    }

    @Override
    public Object getPageData() {
        return this.pageData;
    }

    @Override
    public String toString() {
        return ObjectUtils.impl().reflectionToString(this);
    }
}
