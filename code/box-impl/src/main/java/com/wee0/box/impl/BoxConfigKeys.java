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

package com.wee0.box.impl;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:22
 * @Description 框架配置属性键名
 * <pre>
 * 键名统一汇总在此处，避免使用人员到处查找。
 * </pre>
 **/
public interface BoxConfigKeys {

    /**
     * 框架可定制组件定制类
     */
    String configObject = "configObject";

    /**
     * 全局默认编码
     */
    String encoding = "encoding";

    /**
     * 应用主机域，用于需要识别主机环境的组件中，如cookie。
     */
    String domain = "domain";

    /**
     * 扫描包名称配置
     */
    String scanBasePackage = "scan.basePackage";

    /**
     * 业务编码枚举类配置项名称，多个之间用逗号隔开。
     */
    String bizCodeEnums = "bizCodeManager.bizCodeEnums";

    /**
     * 业务编码文本默认值初始化器配置项名称，多个之间用逗号隔开。
     */
    String bizCodeInitializers = "bizCodeManager.bizCodeInitializers";

    /**
     * 业务编码存储对象, 需要国际化支持时可以设置为："i18nStore"。
     */
    String bizCodeStore = "bizCodeManager.bizCodeStore";

}
