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

import com.wee0.box.beans.annotation.BoxBean;
import com.wee0.box.code.IBizCode;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.testBeans.BeanA;
import com.wee0.box.testObjects.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:38
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for inheritance")
public class IClassUtilsTest {
    // 测试类包名称
    private static final String PACKAGE_BEANS_TEST = "com.wee0.box";
//    private static final String PACKAGE_BEANS_TEST1 = "com.wee0.box.**.test";
//    private static final String PACKAGE_BEANS_TEST2 = "com.wee0.box.*.test";

    // 实现类实例
    protected static IClassUtils classUtils;

    @Test
    public void testGetClassesByAnnotation() {
        Set<Class<?>> _classes = classUtils.getClassesByAnnotation(PACKAGE_BEANS_TEST, BoxBean.class);
        Assert.assertNotNull(_classes);
        Assert.assertEquals(5, _classes.size());
    }

    @Test
    public void testGetClassesByInterface() {
        Set<Class<?>> _classes = classUtils.getClassesByInterface(PACKAGE_BEANS_TEST, InterfaceA.class);
        Assert.assertNotNull(_classes);
        Assert.assertEquals(3, _classes.size());

        _classes = classUtils.getClassesByInterface(PACKAGE_BEANS_TEST, InterfaceB.class);
        Assert.assertNotNull(_classes);
        Assert.assertEquals(3, _classes.size());

        _classes = classUtils.getClassesByInterface(PACKAGE_BEANS_TEST, InterfaceE.class);
        Assert.assertNotNull(_classes);
        Assert.assertTrue(_classes.isEmpty());

        _classes = classUtils.getClassesByInterface(PACKAGE_BEANS_TEST, IBizCode.class);
        Assert.assertNotNull(_classes);
        Assert.assertEquals(1, _classes.size());
    }

    @Test(expected = BoxRuntimeException.class)
    public void testGetClassesByInterfaceEx() {
        classUtils.getClassesByInterface(PACKAGE_BEANS_TEST, BoxBean.class);
    }

    @Test
    public void testResolveClassName() {
        Class _cla = classUtils.resolveClassName("com.wee0.box.testBeans.BeanE");
        Assert.assertNotNull(_cla);
    }

    @Test
    public void testGetClassAllInterfaces() {
        Set<Class<?>> _interfaces = classUtils.getClassAllInterfaces(ClassD.class);
        Assert.assertEquals(2, _interfaces.size());

        _interfaces = classUtils.getClassAllInterfaces(ClassE.class);
        Assert.assertTrue(_interfaces.isEmpty());
    }

    @Test
    public void testGetClassShortNameAsProperty() {
        String _propertyName = classUtils.getClassShortNameAsProperty(BeanA.class);
        Assert.assertEquals("beanA", _propertyName);

        _propertyName = classUtils.getClassShortNameAsProperty(IBizCode.class);
        Assert.assertEquals("IBizCode", _propertyName);
        Assert.assertNotEquals("iBizCode", _propertyName);
    }

    @Test
    public void testHasMethod() {
        //:TODO 待补充
        Assert.assertTrue(classUtils.hasMethod(ClassA.class, "toString"));
    }

    @Test
    public void testGetMethod() {
        //:TODO 待补充
    }
}
