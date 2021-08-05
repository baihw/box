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

package com.wee0.box.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/6/6 7:33
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Disabled("for inheritance")
public class IPathUtilsTest {

    // 实现类实例
    protected static IPathUtils impl;

    @Test
    public void separatorToForwardSlash() {
        Assertions.assertNull(impl.separatorToForwardSlash(""));
        Assertions.assertNull(impl.separatorToForwardSlash(" "));
        Assertions.assertNull(impl.separatorToForwardSlash("    "));
        Assertions.assertNull(impl.separatorToForwardSlash("         "));

        Assertions.assertEquals(impl.separatorToForwardSlash("\\\\a\\\\b\\\\c"), "/a/b/c");
        Assertions.assertEquals(impl.separatorToForwardSlash("a\\\\b\\\\c"), "a/b/c");
        Assertions.assertEquals(impl.separatorToForwardSlash("\\\\a\\\\b\\\\c\\\\"), "/a/b/c/");
        Assertions.assertEquals(impl.separatorToForwardSlash("\\\\\\\\a\\\\b\\\\\\\\\\\\\\c\\d"), "/a/b/c/d");
        Assertions.assertEquals(impl.separatorToForwardSlash("\\\\a/b\\\\c/d"), "/a/b/c/d");
        Assertions.assertEquals(impl.separatorToForwardSlash("\\\\a///b\\\\c//d"), "/a/b/c/d");
    }

    @Test
    public void separatorToBackSlash() {
        Assertions.assertNull(impl.separatorToBackSlash(""));
        Assertions.assertNull(impl.separatorToBackSlash(" "));
        Assertions.assertNull(impl.separatorToBackSlash("    "));
        Assertions.assertNull(impl.separatorToBackSlash("         "));

        Assertions.assertEquals(impl.separatorToBackSlash("///a//b/c/////d"), "\\\\a\\\\b\\\\c\\\\d");
        Assertions.assertEquals(impl.separatorToBackSlash("a//b/c/////d"), "a\\\\b\\\\c\\\\d");
        Assertions.assertEquals(impl.separatorToBackSlash("a//b/c/////"), "a\\\\b\\\\c\\\\");
        Assertions.assertEquals(impl.separatorToBackSlash("///a\\b/c\\\\\\\\\\\\\\\\d"), "\\\\a\\\\b\\\\c\\\\d");
    }

}
