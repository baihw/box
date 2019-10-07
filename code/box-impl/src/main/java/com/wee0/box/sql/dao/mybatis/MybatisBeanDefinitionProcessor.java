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

import com.wee0.box.beans.IBeanDefinitionProcessor;
import com.wee0.box.sql.annotation.BoxDao;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/22 7:22
 * @Description Mybatis相关组件处理支持
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MybatisBeanDefinitionProcessor implements IBeanDefinitionProcessor {

    // 接口工厂类名称
    private static final String NAME_API_FACTORY_BEAN = MybatisDaoFactoryBean.class.getName();

    @Override
    public void foundBean(Class<?> clazz) {
        if (clazz.isInterface() && null != AnnotationUtils.findAnnotation(clazz, BoxDao.class)) {
            MyBatisDaoManager.me().registerDao(clazz);
        }
    }

    @Override
    public String getFactoryBeanClassName(Class clazz) {
        if (clazz.isInterface() && null != AnnotationUtils.findAnnotation(clazz, BoxDao.class))
            return NAME_API_FACTORY_BEAN;
        return null;
    }

    @Override
    public Object getInstance(Class clazz) {
//        if (clazz.isInterface() && clazz.isAnnotationPresent(BoxDao.class))
        if (clazz.isInterface() && null != AnnotationUtils.findAnnotation(clazz, BoxDao.class))
            return MyBatisDaoManager.me().getDao(clazz);
        return null;
    }

}
