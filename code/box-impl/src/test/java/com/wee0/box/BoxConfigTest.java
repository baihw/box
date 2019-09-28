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

package com.wee0.box;

import com.wee0.box.testObjects.InterfaceA;
import com.wee0.box.testObjects.InterfaceC;
import com.wee0.box.testObjects.SingleInstanceClassB;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:37
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxConfigTest {
    @Test
    public void test01() {
        Assert.assertNotNull(BoxConfig.impl());
    }

    @Test
    public void testGetSingleInstance() {
        InterfaceC _impl = BoxConfig.getSingleInstance("com.wee0.box.testObjects.SingleInstanceClassA", InterfaceC.class);
        Assert.assertNotNull(_impl);
        SingleInstanceClassB _classB = BoxConfig.getSingleInstance("com.wee0.box.testObjects.SingleInstanceClassB", SingleInstanceClassB.class);
        Assert.assertNotNull(_classB);
    }

    @Test(expected = RuntimeException.class)
    public void testEx01() {
        BoxConfig.getSingleInstance("a.b.C", InterfaceA.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testEx02() {
        BoxConfig.getSingleInstance("com.wee0.box.testObjects.SingleInstanceClassA", SingleInstanceClassB.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testEx03() {
        BoxConfig.getSingleInstance("com.wee0.box.testObjects.SingleInstanceClassA", InterfaceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEx04() {
        BoxConfig.getSingleInstance(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEx05() {
        BoxConfig.getSingleInstance("a.b.C", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEx06() {
        BoxConfig.getSingleInstance("com.wee0.box.testObjects.ClassA", null);
    }

}
