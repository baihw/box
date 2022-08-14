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

package com.wee0.box.spring.util;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.util.IClassUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:11
 * @Description 基于Spring相关类的类处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class SpringClassUtils implements IClassUtils {

    // 匹配包下所有类文件
    private static final String PATTERN_ALL_CLASSES = "/**/*.class";

    @Override
    public Set<Class<?>> getClassesByAnnotation(String packageName, Class<? extends Annotation> annotation) {
        if (null == annotation)
            throw new BoxRuntimeException("annotation can't be null!");
        final String _ANNOTATION = annotation.getName();

        Set<String> _names = doScan(packageName, new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return metadataReader.getAnnotationMetadata().hasAnnotation(_ANNOTATION);
            }
        });

        if (null == _names || _names.isEmpty())
            return Collections.emptySet();

        Set<Class<?>> _result = new HashSet<>(_names.size());
        for (String _name : _names)
            _result.add(resolveClassName(_name));
        return _result;
    }

    @Override
    public Set<Class<?>> getClassesByInterface(String packageName, Class<?> interfaceCla) {
        if (null == interfaceCla)
            throw new BoxRuntimeException("interfaceCla can't be null!");
        if (!interfaceCla.isInterface() || interfaceCla.isAnnotation())
            throw new BoxRuntimeException("interfaceCla must be an interface!");
        final String _INTERFACE_NAME = interfaceCla.getName();

        Set<String> _names = doScan(packageName, new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String[] _names = metadataReader.getClassMetadata().getInterfaceNames();
                if (null == _names || 0 == _names.length)
                    return false;
                for (String _name : _names) {
                    if (_INTERFACE_NAME.equals(_name))
                        return true;
                }
                return false;
            }
        });

        if (null == _names || _names.isEmpty())
            return Collections.emptySet();

        Set<Class<?>> _result = new HashSet<>(_names.size());
        for (String _name : _names)
            _result.add(resolveClassName(_name));
        return _result;
    }

    @Override
    public Class<?> resolveClassName(String className, ClassLoader classLoader) {
        if (null == className || 0 == (className = className.trim()).length())
            throw new BoxRuntimeException("className can't be empty!");
        if (null == classLoader)
            classLoader = ClassUtils.getDefaultClassLoader();
        try {
            return ClassUtils.forName(className, classLoader);
        } catch (ClassNotFoundException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Class<?> resolveClassName(String className) {
        return resolveClassName(className, ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Set<Class<?>> getClassAllInterfaces(Class<?> cla, ClassLoader classLoader) {
        return ClassUtils.getAllInterfacesForClassAsSet(cla, classLoader);
    }

    @Override
    public Set<Class<?>> getClassAllInterfaces(Class<?> cla) {
        return getClassAllInterfaces(cla, ClassUtils.getDefaultClassLoader());
    }

    @Override
    public boolean hasMethod(Class<?> cla, String methodName, Class<?>... paramTypes) {
        return ClassUtils.hasMethod(cla, methodName, paramTypes);
    }

    @Override
    public Method getMethod(Class<?> cla, String methodName, Class<?>... paramTypes) {
        return ClassUtils.getMethod(cla, methodName, paramTypes);
    }

    @Override
    public Class<?>[] getMethodGenericReturnType(Method method, Class clazz) {
        ResolvableType _resolvableType = ResolvableType.forMethodReturnType(method, clazz);
        if (_resolvableType.hasGenerics()) {
            ResolvableType[] _types = _resolvableType.getGenerics();
            Class<?>[] _result = new Class<?>[_types.length];
            for (int _i = 0; _i < _types.length; _i++) {
                ResolvableType _type = _types[_i];
                _result[_i] = _type.resolve();
            }
        }
        return new Class[0];
    }

    @Override
    public String getClassShortNameAsProperty(Class<?> cla) {
        if (null == cla)
            throw new BoxRuntimeException("cla can't be null!");
        return ClassUtils.getShortNameAsProperty(cla);
    }

    /**
     * 执行扫描，获取符合过滤条件的类名称
     *
     * @param packageName 包名称
     * @param typeFilter  类型过滤器
     * @return 符合过滤条件的类名称
     */
    protected Set<String> doScan(String packageName, TypeFilter typeFilter) {
        if (null == packageName || 0 == (packageName = packageName.trim()).length())
            throw new BoxRuntimeException("packageName can't be empty!");
        if (null == typeFilter)
            throw new BoxRuntimeException("typeFilter can't be null!");

        Set<String> _result = new HashSet<>();

        ResourcePatternResolver _resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory _metadataReaderFactory = new CachingMetadataReaderFactory(_resourcePatternResolver);

        String _resourcePattern = getPackageClassesPattern(packageName);
        try {
            Resource[] _resources = _resourcePatternResolver.getResources(_resourcePattern);
            for (Resource _resource : _resources) {
                MetadataReader _metadataReader = _metadataReaderFactory.getMetadataReader(_resource);
                if (typeFilter.match(_metadataReader, _metadataReaderFactory)) {
                    _result.add(_metadataReader.getClassMetadata().getClassName());
                }
            }
        } catch (IOException e) {
            throw new BoxRuntimeException(e);
        }
        return _result;
    }

    /**
     * 获取指定包下所有类文件匹配表达式
     *
     * @param packageName 包名称
     * @return 匹配表达式
     */
    static String getPackageClassesPattern(String packageName) {
        if ('.' == packageName.charAt(packageName.length() - 1)) {
            packageName = packageName.substring(0, packageName.length() - 1);
        }
        StringBuilder _sb = new StringBuilder();
        _sb.append(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX);
        _sb.append(packageName.replace('.', '/'));
        _sb.append(PATTERN_ALL_CLASSES);
        return _sb.toString();
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SpringClassUtils() {
        if (null != SpringClassUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SpringClassUtilsHolder {
        private static final SpringClassUtils _INSTANCE = new SpringClassUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SpringClassUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SpringClassUtils me() {
        return SpringClassUtilsHolder._INSTANCE;
    }

}
