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

package com.wee0.box.beans.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 22:01
 * @Description 可扫描组件
 * <pre>
 * 补充说明
 * </pre>
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BoxBean {
    /**
     * @return 组件标识
     */
    String name() default "";

    /**
     * @return 是否每次创建
     */
    boolean prototype() default false;

    /**
     * @return 初始化方法名称
     */
    String initMethod() default "";

    /**
     * @return 销毁方法名称
     */
    String destroyMethod() default "";
}
