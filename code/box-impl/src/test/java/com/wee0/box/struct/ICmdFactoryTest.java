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

package com.wee0.box.struct;

import com.wee0.box.code.BizCodeDef;
import com.wee0.box.code.BizCodeManager;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:07
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for inheritance")
public class ICmdFactoryTest {

    // 实现类实例
    protected static ICmdFactory impl;

    @Test
    public void test01() {
        CMD<String> _result = impl.create("test");
        Assert.assertNotNull(_result);
        Assert.assertTrue(_result.isOK());
        Assert.assertEquals("test", _result.getMessage());
    }

    @Test
    public void test02() {
        CMD<String> _result = impl.create(BizCodeDef.SaveSuccess);
        Assert.assertNotNull(_result);
        Assert.assertTrue(_result.isOK());
        Assert.assertEquals(BizCodeManager.impl().getText(BizCodeDef.SaveSuccess), _result.getMessage());
    }

}
