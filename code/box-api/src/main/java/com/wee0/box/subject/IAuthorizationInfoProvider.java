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

package com.wee0.box.subject;

import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/21 9:22
 * @Description 授权信息数据提供者
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IAuthorizationInfoProvider {

    /**
     * 默认使用的bean名称
     */
    String DEF_BEAN_NAME = "defaultCustomDataProvider";

    /**
     * 获取指定标识用户拥有的角色名称集合
     *
     * @param userId 用户唯一标识
     * @return 角色名称集合
     */
    Set<String> getRoles(String userId);

    /**
     * 获取指定标识用户拥有的权限名称集合
     *
     * @param userId 用户唯一标识
     * @return 权限名称集合
     */
    Set<String> getPermissions(String userId);

}
