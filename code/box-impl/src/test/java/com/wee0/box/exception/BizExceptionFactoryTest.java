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

package com.wee0.box.exception;

import com.wee0.box.code.impl.SimpleBizCodeManager;
import com.wee0.box.testObjects.BizCodeExt1;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:35
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BizExceptionFactoryTest {

    @BeforeClass
    public static void setup() {
        SimpleBizCodeManager.me().addBizCodeInitializer(BizCodeExt1.Ext1Test1);
        SimpleBizCodeManager.me().init();
    }

    @Test(expected = BizException.class)
    public void testEx01() {
        throw BizExceptionFactory.create();
    }

    @Test(expected = BizException.class)
    public void testEx02() {
        throw BizExceptionFactory.create(BizCodeExt1.Ext1Test1);
    }

    @Test(expected = BizException.class)
    public void testEx03() {
        throw BizExceptionFactory.create(BizCodeExt1.Ext1Test2, "0", "1", "2", "3", "4", "5", "6");
    }

}
