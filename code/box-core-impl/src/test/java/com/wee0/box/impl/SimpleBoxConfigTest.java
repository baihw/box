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

package com.wee0.box.impl;

import com.wee0.box.IBoxConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:36
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBoxConfigTest {

    // 实例对象
    private static final IBoxConfig CONFIG = SimpleBoxConfig.me();

    @Test
    public void test02GetResourceDir() {
        String _resourceDir = CONFIG.getResourceDir();
        Assertions.assertNotNull(_resourceDir);
        File _f = new File(_resourceDir);
        Assertions.assertTrue(_f.exists());
        Assertions.assertTrue(_f.isDirectory());
    }

    @Test
    public void test03GetResourcePath() {
        String _resource = "\\spring\\applicationContext-common.xml";
        String _resourcePath = CONFIG.getResourcePath(_resource);
        Assertions.assertTrue(_resourcePath.endsWith("spring/applicationContext-common.xml"));
    }

}
