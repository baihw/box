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
 * @CreateDate 2019/8/31 7:39
 * @Description 业务编码管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IBizCodeManager {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.code.impl.SimpleBizCodeManager";

    /**
     * 增加业务编码枚举类
     *
     * @param bizCodeEnum 业务编码枚举类
     * @param <T>         类型
     */
    <T extends Enum<T> & IBizCode> void addBizCodeEnum(Class<T> bizCodeEnum);

    /**
     * 增加业务编码文本默认值初始化器
     *
     * @param initializer 初始化器
     */
    void addBizCodeInitializer(IBizCodeInitializer initializer);

    /**
     * 获取指定业务编码对应的文本
     *
     * @param bizCode 业务编码
     * @return 文本
     */
    String getText(IBizCode bizCode);

    /**
     * 获取指定业务编码的描述对象
     *
     * @param bizCode 业务编码
     * @param args    消息参数
     * @return 业务编码描述对象
     */
    IBizCodeInfo getCodeInfo(IBizCode bizCode, String... args);

}
