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

package com.wee0.box.task;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/12 12:12
 * @Description 任务管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ITaskManager {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.task.impl.SimpleTaskManager";

    /**
     * 执行一个异步任务，不关注执行结果。
     *
     * @param runnable 任务执行逻辑
     */
    void asyncRun(Runnable runnable);

    /**
     * 执行一个异步任务，获取执行结果。
     *
     * @param supplier 任务执行逻辑
     * @param <U>      结果类型
     * @return 执行结果获取对象
     */
    <U> CompletableFuture<U> asyncRun(Supplier<U> supplier);

}
