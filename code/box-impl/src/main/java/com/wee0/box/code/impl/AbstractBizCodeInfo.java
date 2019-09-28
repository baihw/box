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

package com.wee0.box.code.impl;

import com.wee0.box.code.IBizCode;
import com.wee0.box.code.IBizCodeInfo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:16
 * @Description 业务代码描述对象抽象基类
 * <pre>
 * 补充说明
 * </pre>
 **/
public abstract class AbstractBizCodeInfo implements IBizCodeInfo {

    // 业务代码
    protected final IBizCode bizCode;
    // 业务代码对应的文本
    protected final String text;
    // 文本参数
    protected final String[] args;

    public AbstractBizCodeInfo(IBizCode bizCode, String text, String[] args) {
        if (null == bizCode || null == bizCode.getCode())
            throw new IllegalArgumentException("bizCode can't be null!");
        this.bizCode = bizCode;
        this.text = text;
        this.args = args;
    }

    public AbstractBizCodeInfo(IBizCode bizCode, String text) {
        this(bizCode, text, null);
    }

    @Override
    public String getCode() {
        return this.bizCode.getCode();
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String[] getTextArgs() {
        return this.args;
    }

    @Override
    public String formatText() {
        return formatText(this.args);
    }

}
