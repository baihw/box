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

package com.wee0.box.log.log4j2;

import com.wee0.box.log.ILoggerTest;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:12
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Log4j2LoggerTest extends ILoggerTest {

//    static {
//        System.setProperty("log4j2.level", "DEBUG");
//    }

    @BeforeAll
    public static void setUp() {
        log = Log4j2LoggerFactory.me().getLogger(Log4j2LoggerTest.class);
    }

//    @Test
//    public void testLog4j2Logger() {
//        Logger _log = LogManager.getLogger(Log4j2LoggerTest.class);
//        _log.trace("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 0);
//        _log.debug("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 1);
//        _log.info("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 2);
//        _log.warn("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 3);
//        _log.error("logger - class:{}, name:{}, p1:{}", _log.getClass(), _log.getName(), 4);
//    }

}
