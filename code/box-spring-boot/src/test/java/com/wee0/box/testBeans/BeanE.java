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

package com.wee0.box.testBeans;

import com.wee0.box.beans.annotation.BoxBean;
import com.wee0.box.testObjects.InterfaceA;
import com.wee0.box.testObjects.InterfaceB;

import javax.annotation.Resource;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:59
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@BoxBean
public class BeanE {

    @Resource(name = "beanA")
    private InterfaceA interfaceA1;

    @Resource(name = "beanB")
    protected InterfaceA interfaceA2;

    @Resource(name = "beanA")
    public InterfaceA interfaceA3;

    @Resource(name = "beanA")
    InterfaceA interfaceA4;

    @Resource
    private InterfaceB interfaceB1;

    public InterfaceA getInterfaceA1() {
        return interfaceA1;
    }

    public InterfaceA getInterfaceA2() {
        return interfaceA2;
    }

    public InterfaceA getInterfaceA3() {
        return interfaceA3;
    }

    public InterfaceA getInterfaceA4() {
        return interfaceA4;
    }

    public InterfaceB getInterfaceB1() {
        return interfaceB1;
    }

}
