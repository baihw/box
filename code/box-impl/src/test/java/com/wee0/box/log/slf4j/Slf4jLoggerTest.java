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

package com.wee0.box.log.slf4j;

import com.wee0.box.log.ILogger;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:16
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Slf4jLoggerTest {

    @Test
    public void test1() {
        Logger _log = LoggerFactory.getLogger(Slf4jLoggerTest.class);
        _log.trace("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 0);
        _log.debug("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 1);
        _log.info("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 2);
        _log.warn("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 3);
        _log.error("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 4);
    }

    @Test
    public void test2() {
        ILogger _log = Slf4jLoggerFactory.me().getLogger(Slf4jLoggerTest.class);
        _log.trace("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 0);
        _log.debug("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 1);
        _log.info("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 2);
        _log.warn("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 3);
        _log.error("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 4);
    }

}
