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

package com.wee0.box.plugins.office;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 8:18
 * @Description excel操作工具类
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IExcelUtils {

    /**
     * 根据指定的数据集生成excel输出流
     *
     * @param outStream   excel输出流
     * @param data        数据集
     * @param headerNames 表头名称集合。不包含在表头中的键值数据将不会被使用。如果未指定，取数据集中的第一条记录作为表头。
     */
    void mapListToExcel(OutputStream outStream, List<Map<String, Object>> data, String[] headerNames);

    /**
     * 根据指定的数据集生成excel输出流
     *
     * @param outStream excel输出流
     * @param data      数据集
     */
    default void mapListToExcel(OutputStream outStream, List<Map<String, Object>> data) {
        mapListToExcel(outStream, data, null);
    }

}
