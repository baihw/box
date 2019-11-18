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

import com.wee0.box.util.shortcut.ValidateUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 22:36
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for inheritance")
public class IValidateUtilsTest {
    // 实现类实例
    protected static IValidateUtils impl;

    @Test
    public void validateStringLength() {
        Assert.assertTrue(impl.validateStringLength(null, 3, 6, true));
        Assert.assertTrue(impl.validateStringLength("", 3, 6, true));
        Assert.assertTrue(impl.validateStringLength(" ", 3, 6, true));
        Assert.assertTrue(impl.validateStringLength("123", 3, 6, true));
        Assert.assertTrue(impl.validateStringLength("123456", 3, 6, true));

        Assert.assertFalse(impl.validateStringLength(null, 3, 6, false));
        Assert.assertFalse(impl.validateStringLength("", 3, 6, false));
        Assert.assertFalse(impl.validateStringLength(" ", 3, 6, false));
        Assert.assertFalse(impl.validateStringLength("12", 3, 6, true));
        Assert.assertFalse(impl.validateStringLength("1234567", 3, 6, true));
    }

    @Test
    public void validateUrl() {
        Assert.assertTrue(impl.validateUrl("http://www.wee0.com"));
        Assert.assertTrue(impl.validateUrl("https://www.wee0.com"));

        Assert.assertFalse(impl.validateUrl("http1://www.wee0.com"));
        Assert.assertFalse(impl.validateUrl("https1://www.wee0.com"));
    }

}
