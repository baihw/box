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
 * @CreateDate 2019/9/15 10:52
 * @Description 一个简单的分页数据实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimplePageData implements IPageData {

    private long pageNum;
    private long pageSize;
    private long pageCount;
    private Collection pageData;
    private long dataCount;

    @Override
    public long getPageNum() {
        return this.pageNum;
    }

    @Override
    public long getPageSize() {
        return this.pageSize;
    }

    @Override
    public long getPageCount() {
        return this.pageCount;
    }

    @Override
    public <T> Collection<T> getPageData() {
        return this.pageData;
    }

    @Override
    public long getDataCount() {
        return this.dataCount;
    }

}
