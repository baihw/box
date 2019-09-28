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

package com.wee0.box.struct;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:50
 * @Description 编码-消息-数据
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface CMD<CT> extends IStruct {

    /**
     * 设置编码
     *
     * @param code 编码
     * @return 链式调用对象
     */
    CMD<CT> setCode(CT code);

    /**
     * 获取编码
     *
     * @return 编码
     */
    CT getCode();

    /**
     * 设置消息
     *
     * @param msg 消息
     * @return 链式调用对象
     */
    CMD<CT> setMsg(String msg);

    /**
     * 获取消息
     *
     * @return 消息
     */
    String getMsg();

    /**
     * 设置数据
     *
     * @param data 数据
     * @return 链式调用对象
     */
    CMD<CT> setData(Object data);

    /**
     * 获取数据
     *
     * @return 数据
     */
    Object getData();

    /**
     * 根据状态值的设置判断是否正常状态
     *
     * @return 是否正常状态
     */
    boolean isOK();
}
