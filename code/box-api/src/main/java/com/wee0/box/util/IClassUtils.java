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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:03
 * @Description 类处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IClassUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SpringClassUtils";

    /**
     * 从指定包下扫描所有标记了指定注解的类
     *
     * @param packageName 包名称
     * @param annotation  注解类
     * @return 扫描出的类
     */
    Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotation);

    /**
     * 从指定包下扫描所有声明实现了指定接口的类
     *
     * @param packageName  包名称
     * @param interfaceCla 接口类
     * @return 扫描出的类
     */
    Set<Class<?>> getClassesByInterface(String packageName, Class<?> interfaceCla);

    /**
     * 使用指定类加载器解析类名称获取类对象
     *
     * @param className   类名称
     * @param classLoader 类加载器
     * @return 类对象
     */
    Class<?> resolveClassName(String className, ClassLoader classLoader);

    /**
     * 使用默认类加载器解析类名称获取类对象
     *
     * @param className 类名称
     * @return 类对象
     */
    Class<?> resolveClassName(String className);

    /**
     * 获取指定类实现的所有接口
     *
     * @param cla         类
     * @param classLoader 类加载器
     * @return 接口集合
     */
    Set<Class<?>> getClassAllInterfaces(Class<?> cla, ClassLoader classLoader);

    /**
     * 获取指定类实现的所有接口
     *
     * @param cla 类
     * @return 接口集合
     */
    Set<Class<?>> getClassAllInterfaces(Class<?> cla);

    /**
     * 判断类上是否具有指定签名的公开方法
     *
     * @param cla        类
     * @param methodName 方法名称
     * @param paramTypes 方法参数
     * @return true / false
     */
    boolean hasMethod(Class<?> cla, String methodName, Class<?>... paramTypes);

    /**
     * 获取类上指定签名的公开方法
     *
     * @param cla        类
     * @param methodName 方法名称
     * @param paramTypes 方法参数
     * @return 方法
     */
    Method getMethod(Class<?> cla, String methodName, Class<?>... paramTypes);

    /**
     * 获取方法返回对象中的泛型参数实际类型
     *
     * @param method 方法对象
     * @param clazz  方法关联类
     * @return 泛型参数实际类型集合
     */
    Class<?>[] getMethodGenericReturnType(Method method, Class clazz);

    /**
     * 获取类短名称属性表示方式。
     *
     * @param cla 类
     * @return 短名称属性表示名称
     */
    String getClassShortNameAsProperty(Class<?> cla);


}
