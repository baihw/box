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

import com.wee0.box.BoxConfig;
import com.wee0.box.code.IBizCode;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:52
 * @Description CMD类型工厂快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class CmdFactory {

    // 实现类实例
    private static final ICmdFactory IMPL = BoxConfig.impl().getInterfaceImpl(ICmdFactory.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static ICmdFactory impl() {
        return IMPL;
    }

    /**
     * 根据指定设置创建数据对象
     *
     * @param code 编码值
     * @param msg  消息
     * @param data 数据
     * @return 数据对象
     */
    public static CMD<String> create(String code, String msg, Object data) {
        return IMPL.create(code, msg, data);
    }

    /**
     * 根据指定设置创建数据对象
     *
     * @param data 数据
     * @return 数据对象
     */
    public static CMD<String> create(Object data) {
        return IMPL.create(data);
    }

    /**
     * 根据指定设置创建数据对象
     *
     * @param code 编码值
     * @param msg  消息
     * @return 数据对象
     */
    public static CMD<String> create(String code, String msg) {
        return IMPL.create(code, msg);
    }

    /**
     * 根据指定设置创建数据对象
     *
     * @param msg 消息
     * @return 数据对象
     */
    public static CMD<String> create(String msg) {
        return IMPL.create(msg);
    }

    /**
     * 根据业务编码对应的消息创建数据对象
     *
     * @param bizCode 业务编码
     * @return 数据对象
     */
    public static CMD<String> create(IBizCode bizCode) {
        return IMPL.create(bizCode);
    }

    /**
     * 根据业务编码对应的消息创建数据对象
     *
     * @param bizCode 业务编码
     * @param args    消息参数
     * @return 数据对象
     */
    public static CMD<String> create(IBizCode bizCode, String... args) {
        return IMPL.create(bizCode, args);
    }

}
