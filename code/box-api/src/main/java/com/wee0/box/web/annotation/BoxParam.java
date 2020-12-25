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

package com.wee0.box.web.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/8/30 7:02
 * @Description 作用于参数上的Action参数标识注解
 * <pre>
 * 补充说明
 * </pre>
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BoxParam {

    /**
     * @return 参数名称
     */
    String name() default "";

    /**
     * @return 校验规则，默认只要有输入就算通过。
     */
    String pattern() default "*";

    /**
     * @return 参数不合法时的提示信息，默认为：参数非法！
     */
    String message() default "参数非法!";

    /**
     * @return 是否允许空值
     */
    boolean allowNull() default false;

}
