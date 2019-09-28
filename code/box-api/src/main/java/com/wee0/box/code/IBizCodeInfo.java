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

package com.wee0.box.code;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:35
 * @Description 业务编码描述对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IBizCodeInfo {

    /**
     * @return 业务编码
     */
    String getCode();

    /**
     * @return 业务编码对应的文本
     */
    String getText();

    /**
     * @return 文本参数
     */
    String[] getTextArgs();

    /**
     * 使用自身的文本参数替换文本中的参数占位符
     *
     * @return 格式化后的文本
     */
    String formatText();

    /**
     * 使用给定的文本参数替换文本中的参数占位符
     *
     * @param args 参数值
     * @return 格式化后的文本
     */
    String formatText(String... args);

}
