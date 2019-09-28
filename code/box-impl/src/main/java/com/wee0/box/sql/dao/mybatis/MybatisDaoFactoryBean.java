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

package com.wee0.box.sql.dao.mybatis;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/28 13:04
 * @Description Mybatis数据访问对象创建工厂组件
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MybatisDaoFactoryBean<T> implements FactoryBean<T> {

    /**
     * 接口类
     */
    private Class<T> interfaceClass;

    @Override
    public T getObject() throws Exception {
        return MyBatisDaoManager.me().getDao(this.interfaceClass);
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

}
