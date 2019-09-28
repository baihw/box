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

package com.wee0.box.spring;

import com.wee0.box.beans.annotation.BoxAction;
import com.wee0.box.beans.annotation.BoxBean;
import com.wee0.box.beans.annotation.BoxService;
import com.wee0.box.beans.annotation.BoxServiceApi;
import com.wee0.box.sql.annotation.BoxDao;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 9:05
 * @Description 内部通用工具类
 * <pre>
 * 补充说明
 * </pre>
 **/
final class InternalUtils {

    // 当前系统支持的自动注册组件注解类型集合。
    static final List<Class<? extends Annotation>> AUTO_ANNOTATIONS = new ArrayList<Class<? extends Annotation>>() {{
        add(BoxBean.class);
        add(BoxDao.class);
        add(BoxAction.class);
//        add(BoxServiceApi.class);
//        add(BoxService.class);
    }};

    /**
     * 解析多个逗号隔开的包路径字符串为路径数组
     *
     * @param packages 包路径字符串
     * @return 路径数组
     */
    static String[] parsePackages(String packages) {
        if (null == packages)
            return null;
        String[] _result = null;
        if (-1 != packages.indexOf(',')) {
            String[] _packageArr = packages.split(",");
            List<String> _packageList = new ArrayList<>(_packageArr.length);
            for (String _package : _packageArr) {
                if (null == _package || 0 == (_package = _package.trim()).length())
                    continue;
                _packageList.add(_package);
            }
            _result = _packageList.toArray(new String[]{});
        } else {
            _result = new String[]{packages};
        }
        return _result;
    }

    /**
     * 判断是否是接口代理类Bean名称
     *
     * @param beanName Bean名称
     * @return true / false
     */
    static boolean isInterfaceBeanName(String beanName) {
        if (null == beanName || 0 == beanName.length())
            return false;
        return '@' == beanName.charAt(0);
    }

    /**
     * 将接口代理类Bean名称转换为常规Bean名称
     *
     * @param beanName Bean名称
     * @return 常规Bean名称
     */
    static String interfaceBeanNameToBeanName(String beanName) {
        if (null == beanName || 0 == beanName.length())
            return beanName;
        return beanName.substring(1);
    }

    /**
     * 生成接口代理类Bean名称
     *
     * @param interfaceClassName 接口类名称
     * @return 代理类Bean名称
     */
    static String generateInterfaceBeanName(String interfaceClassName) {
        String _interfaceBeanName = generateBeanName(interfaceClassName);
        return "@".concat(_interfaceBeanName);
    }

    /**
     * 生成组件类Bean名称
     *
     * @param beanClassName 组件类名称
     * @return 实现类Bean名称
     */
    static String generateBeanName(String beanClassName) {
        String _shortClassName = ClassUtils.getShortName(beanClassName);
        char _nameChars[] = _shortClassName.toCharArray();
        if (_nameChars.length > 1 && 'I' == _shortClassName.charAt(0) && Character.isUpperCase(_shortClassName.charAt(1))) {
            _nameChars = Arrays.copyOfRange(_nameChars, 1, _nameChars.length);
        }
        if (_nameChars.length > 1 && Character.isUpperCase(_nameChars[1]) && Character.isUpperCase(_nameChars[0])) {
            return new String(_nameChars);
        }
        _nameChars[0] = Character.toLowerCase(_nameChars[0]);
        return new String(_nameChars);
    }

}
