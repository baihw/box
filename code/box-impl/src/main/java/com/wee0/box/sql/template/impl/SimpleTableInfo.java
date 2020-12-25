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

package com.wee0.box.sql.template.impl;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:25
 * @Description 一个简单的表信息描述对象
 * <pre>
 * 补充说明
 * </pre>
 **/
final class SimpleTableInfo {

    // 表名
    private String name;
    // 表注释
    private String comment;
    // 列集合
    private List<Map<String, Object>> columns;

    SimpleTableInfo(String name, String comment, List<Map<String, Object>> columns) {
        this.name = name;
        this.comment = comment;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public List<Map<String, Object>> getColumns() {
        return columns;
    }


}
