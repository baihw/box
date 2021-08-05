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

package com.wee0.box.log;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/29 7:12
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Disabled("for inheritance")
public class ILoggerTest {

    // 实现类实例
    protected static ILogger log;

    @Test
    public void testBoxLogger() {
        log.trace("logger - class:{}, name:{}, p1:{}", log.getClass(), log.getName(), 0);
        log.debug("logger - class:{}, name:{}, p1:{}", log.getClass(), log.getName(), 1);
        log.info("logger - class:{}, name:{}, p1:{}", log.getClass(), log.getName(), 2);
        log.warn("logger - class:{}, name:{}, p1:{}", log.getClass(), log.getName(), 3);
        log.error("logger - class:{}, name:{}, p1:{}", log.getClass(), log.getName(), 4);
    }

}
