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

package com.wee0.box.util;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/6/6 7:30
 * @Description 路径处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IPathUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimplePathUtils";


    /**
     * 将路径中的分隔符统一为正斜杠，并去除冗余的分隔符。兼容windows与linux系统，适用于大部分场景。
     *
     * @param path 路径字符串
     * @return 统一分隔符为正斜杠的路径
     */
    String separatorToForwardSlash(String path);

    /**
     * 将路径中的分隔符统一为反斜杠，并去除冗余的分隔符。适用于调用windows命令行时使用。
     *
     * @param path 路径字符串
     * @return 统一分隔符为反斜杠的路径
     */
    String separatorToBackSlash(String path);

}
