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

package com.wee0.box.testDaos;

import com.wee0.box.sql.annotation.BoxDao;
import com.wee0.box.sql.dao.IBaseDao;
import com.wee0.box.testEntities.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/7 7:08
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@BoxDao
public interface ISysUserDao extends IBaseDao<SysUserEntity, Integer> {

    /**
     * 获取用户列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> findUserList(Map<String, Object> params);

    /**
     * 获取用户简单列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> findUserSimpleList(Map<String, Object> params);

    /**
     * 根据登录名统计用户数量，作唯一性判断使用
     *
     * @param loginName 登陆名称
     * @return
     */
    public Integer countSysUserByLoginName(String loginName);

}
