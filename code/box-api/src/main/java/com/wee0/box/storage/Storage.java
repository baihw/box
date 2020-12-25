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

package com.wee0.box.storage;

import com.wee0.box.BoxConfig;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 7:19
 * @Description 存储操作对象快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class Storage {
    // 实现类实例
    private static final IStorage IMPL = BoxConfig.impl().getInterfaceImpl(IStorage.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IStorage impl() {
        return IMPL;
    }

}
