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

package com.wee0.box.task.impl;

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.task.ITaskManager;

import java.io.ObjectStreamException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/12 12:19
 * @Description 一个简单的任务管理器实现。
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleTaskManager implements ITaskManager {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleTaskManager.class);

    @Override
    public void asyncRun(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    @Override
    public <U> CompletableFuture<U> asyncRun(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleTaskManager() {
        if (null != SimpleTaskManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleTaskManagerHolder {
        private static final SimpleTaskManager _INSTANCE = new SimpleTaskManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleTaskManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleTaskManager me() {
        return SimpleTaskManagerHolder._INSTANCE;
    }
}
