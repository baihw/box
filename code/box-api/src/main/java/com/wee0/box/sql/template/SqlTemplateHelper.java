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

package com.wee0.box.sql.template;

import com.wee0.box.BoxConfig;
import com.wee0.box.sql.ISqlHelper;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:06
 * @Description 数据库相关模板生成助手快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SqlTemplateHelper {

    // 实现类实例
    private static final ISqlTemplateHelper IMPL = BoxConfig.impl().getInterfaceImpl(ISqlTemplateHelper.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static ISqlTemplateHelper impl() {
        return IMPL;
    }

}
