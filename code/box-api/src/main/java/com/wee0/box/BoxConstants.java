/*
 * *
 *  * Copyright (c) 2019-present, wee0.com.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 */

package com.wee0.box;

import java.nio.charset.Charset;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 17:01
 * @Description 公共常量
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxConstants {

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * UTF-8
     */
    public static final String UTF8 = "UTF-8";

    /**
     * UTF-8字符集
     */
    public static final Charset UTF8_CHARSET = Charset.forName(UTF8);

    /**
     * 空对象数组
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[]{};

}
