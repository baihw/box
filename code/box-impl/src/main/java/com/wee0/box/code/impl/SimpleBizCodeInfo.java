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
 * @CreateDate 2019/9/1 7:17
 * @Description 一个简单的业务代码描述对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBizCodeInfo extends AbstractBizCodeInfo implements IBizCodeInfo {

    public SimpleBizCodeInfo(IBizCode bizCode, String text, String[] args) {
        super(bizCode, text, args);
    }

    public SimpleBizCodeInfo(IBizCode bizCode, String text) {
        super(bizCode, text);
    }

    /**
     * 使用指定参数替换消息文本中的占位符，当前占位符格式为{0..n}。
     * 如果文本为：参数一：{0}，参数二：{1}，参数三：{2}。
     * 如果参数为：p1, p2, p3。
     * 调用形式如：formatText("p1", "p2", "p3")。
     * 则返回值为：参数一：p1，参数二：p2，参数三：p3。
     *
     * @param args 参数集合
     * @return 格式化文本
     */
    @Override
    public String formatText(String... args) {
        if (null == this.text)
            return null;
        if (null == args || 0 == args.length)
            return this.text;
        String _result = this.text;
        for (int _i = 0, _iLen = args.length; _i < _iLen; _i++) {
            if (null == args[_i])
                continue;
            _result = _result.replace(new String(new char[]{'{', (char) (_i + 48), '}'}), args[_i]);
        }
        return _result;
    }
}
