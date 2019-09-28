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

package com.wee0.box.io.impl;

import org.apache.ibatis.io.DefaultVFS;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 7:18
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for manual")
public class SimpleFileSystemTest {

    @Test
    public void test01() throws IOException {
        List<String> _names;
//        _names = SimpleFileSystem.me().list("mybatis/mapper/");
        _names = SimpleFileSystem.me().list("mybatis/mapper/", (name) -> name.endsWith("Mapper.xml"));
        for (String _name : _names) {
            System.out.println("name: " + _name);
            System.out.println("resource: " + Thread.currentThread().getContextClassLoader().getResource(_name));
            System.out.println("stream: " + Thread.currentThread().getContextClassLoader().getResourceAsStream(_name));
        }
    }

}
