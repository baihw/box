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

package com.wee0.box.util.shortcut;

import com.wee0.box.BoxConfig;
import com.wee0.box.util.IThreadUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:12
 * @Description 线程处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class ThreadUtils {

    // 实现类实例
    private static final IThreadUtils IMPL = BoxConfig.impl().getInterfaceImpl(IThreadUtils.class);

    /**
     * 创建一个线程局部变量管理对象
     *
     * @param <T> 数据类型
     * @return 对象实例
     */
    public static <T> ThreadLocal<T> createThreadLocal() {
        return IMPL.createThreadLocal();
    }

}
