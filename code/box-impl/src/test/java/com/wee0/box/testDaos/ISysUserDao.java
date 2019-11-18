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

import java.util.Date;
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
public interface ISysUserDao extends IBaseDao<SysUserEntity, String> {

    List<SysUserEntity> findAll();

    List<SysUserEntity> finaAllByPage(Map<String, Object> params);

    SysUserEntity findById(String id);

    Map<String, Object> findLimit1();

    List<SysUserEntity> findByCreateTime1(Date createTime);

    List<SysUserEntity> findByCreateTime2(Date createTime);

    Integer updatePassword(String password, String id);

    List<Map<String, Object>> nativeQuery(String sql);

}
