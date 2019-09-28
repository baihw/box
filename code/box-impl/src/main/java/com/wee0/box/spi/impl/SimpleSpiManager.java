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

package com.wee0.box.spi.impl;

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:08
 * @Description 一个简单的spi组件管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleSpiManager {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleSpiManager.class);

    // 组件实例缓存
    private static final ConcurrentHashMap<Class, List> DATA = new ConcurrentHashMap<>(128);

    /**
     * 获取指定类型的扩展组件
     *
     * @param interfaceClass 接口类型
     * @param <T>            类型
     * @return 接口实现者实例集合
     */
    public static <T> List<T> getImplLIst(Class<T> interfaceClass) {
        if (null == interfaceClass)
            return Collections.emptyList();
        if (!interfaceClass.isInterface())
            throw new IllegalArgumentException(interfaceClass + " is not an interface.");
        if (DATA.containsKey(interfaceClass))
            return DATA.get(interfaceClass);
        synchronized (DATA) {
            if (DATA.containsKey(interfaceClass))
                return DATA.get(interfaceClass);
            List<T> _result = new ArrayList<>(8);
            ServiceLoader<T> _loader = ServiceLoader.load(interfaceClass);
            if (null != _loader) {
                Iterator<T> _iterator = _loader.iterator();
                while (_iterator.hasNext()) {
                    _result.add(_iterator.next());
                }
            }
            DATA.putIfAbsent(interfaceClass, _result);
            log.debug("load {} impl list {}.", interfaceClass, _result);
            return _result;
        }
    }

}
