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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 8:16
 * @Description word操作工具类
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IWordUtils {

    /**
     * 默认的占位符匹配模式: ${placeholder}
     */
    String DEF_PLACEHOLDER_PATTERN = "\\$\\{[\\w.]+\\}";

    /**
     * 默认的占位符左侧特征字符长度
     */
    int DEF_PLACEHOLDER_LEFT_LEN = 2;

    /**
     * 默认的占位符右侧特征字符长度
     */
    int DEF_PLACEHOLDER_RIGHT_LEN = 1;

    /**
     * 另外一种常见的占位符匹配模式：[@placeholder]
     */
    String PLACEHOLDER_PATTERN1 = "\\[@[\\w.]+\\]";

    /**
     * 模板处理
     *
     * @param in                 模板文件输入流
     * @param out                模板处理完成后的输出流
     * @param placeholderPattern 模板占位符匹配模式
     * @param placeholderHandler 模板占位符处理逻辑
     */
    void templateProcess(InputStream in, OutputStream out, Pattern placeholderPattern, Function<String, Object> placeholderHandler) throws IOException;

    /**
     * 模板处理
     *
     * @param in                  模板文件输入流
     * @param out                 模板处理完成后的输出流
     * @param placeholderPattern  模板占位符匹配模式
     * @param placeholderLeftLen  占位符左侧特征字符长度
     * @param placeholderRightLen 占位符右侧特征字符长度
     * @param placeholderData     占位符替换时使用的数据集合
     */
    void templateProcess(InputStream in, OutputStream out, Pattern placeholderPattern, int placeholderLeftLen, int placeholderRightLen, Map<String, Object> placeholderData) throws IOException;

    /**
     * 模板处理
     *
     * @param in                 模板文件输入流
     * @param out                模板处理完成后的输出流
     * @param placeholderPattern 模板占位符匹配模式
     * @param placeholderData    占位符替换时使用的数据集合
     */
    void templateProcess(InputStream in, OutputStream out, Pattern placeholderPattern, Map<String, Object> placeholderData) throws IOException;

    /**
     * 模板处理
     *
     * @param in              模板文件输入流
     * @param out             模板处理完成后的输出流
     * @param placeholderData 占位符替换时使用的数据集合
     */
    void templateProcess(InputStream in, OutputStream out, Map<String, Object> placeholderData) throws IOException;

}
