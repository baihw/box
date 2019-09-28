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

package com.wee0.box.i18n;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:15
 * @Description 语言选项
 * <pre>
 * 补充说明
 * </pre>
 **/
public enum Language {

    /**
     * 中文简体
     */
    zh_CN("zh_CN"),

    /**
     * 中文简体
     */
    zh_TW("zh_TW"),

    /**
     * 英文
     */
    en("en");

    // 语言代码
    private final String code;

    Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
