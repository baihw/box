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

package com.wee0.box.spring.boot;

import com.wee0.box.IBoxContext;
import com.wee0.box.testObjects.InterfaceA;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:52
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class AppTest extends SpringBootTestBase {

    private static final Logger log = LoggerFactory.getLogger(AppTest.class);

    @Resource
    private IBoxContext boxContext;

    @Test
    public void test01() {
        log.debug("test01...");
        Assert.assertNotNull(boxContext);
    }

    @Test
    public void test04HasBean() {
        Assert.assertTrue(boxContext.hasBean("boxContext"));
        Assert.assertTrue(boxContext.hasBean("beanA"));
        Assert.assertTrue(boxContext.hasBean("beanB"));
        Assert.assertTrue(boxContext.hasBean("beanC"));
        Assert.assertTrue(boxContext.hasBean("beanD"));
        Assert.assertTrue(boxContext.hasBean("beanE"));
    }

    @Test
    public void test05GetBean() {
        String[] _names = context.getBeanNamesForType(InterfaceA.class);
        Assert.assertEquals(2, _names.length);
        InterfaceA _interfaceA = context.getBean(InterfaceA.class);
        InterfaceA _interfaceA1 = context.getBean("beanA", InterfaceA.class);
        InterfaceA _interfaceA2 = context.getBean("beanB", InterfaceA.class);
        log.debug("interfaceA:{}", _interfaceA);
        log.debug("interfaceA1:{}", _interfaceA1);
        log.debug("interfaceA2:{}", _interfaceA2);
    }

}
