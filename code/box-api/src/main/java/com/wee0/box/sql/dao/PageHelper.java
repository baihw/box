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

package com.wee0.box.sql.dao;

import com.wee0.box.BoxConfig;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 9:52
 * @Description 分页助手快捷操作入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class PageHelper {

    // 实现类实例
    private static final IPageHelper IMPL = BoxConfig.impl().getInterfaceImpl(IPageHelper.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IPageHelper impl() {
        return IMPL;
    }

}
