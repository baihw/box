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

package com.wee0.box.testEntities;

import com.wee0.box.beans.annotation.BoxIgnore;
import com.wee0.box.sql.annotation.BoxColumn;
import com.wee0.box.sql.annotation.BoxId;
import com.wee0.box.sql.annotation.BoxTable;
import com.wee0.box.sql.entity.AbstractEntity;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 8:16
 * @Description 基础实体
 * <pre>
 * 补充说明
 * </pre>
 **/
@BoxIgnore
public class BaseEntity extends AbstractEntity<String> {

    @BoxId(generationType = BoxId.GenerationType.UUID)
    @BoxColumn(name = "ID")
    protected String id;

    /**
     * 创建时间
     */
    @BoxColumn(name = "CREATE_TIME")
    protected Date createTime;

    /**
     * 修改时间
     */
    @BoxColumn(name = "UPDATE_TIME")
    protected Date updateTime;

    /**
     * 创建用户
     */
    @BoxColumn(name = "CREATE_USER")
    protected String createUser;

    /**
     * 更新用户
     */
    @BoxColumn(name = "UPDATE_USER")
    protected String updateUser;

    /**
     * 扩展编号
     */
    @BoxColumn(name = "EXPD_ID")
    protected String expdId;

    /**
     * 删除标志:0-未删除；1-删除；
     */
    @BoxColumn(name = "DEL_IND")
    protected String delInd;

    /**
     * 版本号
     */
    @BoxColumn(name = "VERSION")
    protected int version;

    /**
     * 租户id
     */
    @BoxColumn(name = "TENANT_ID")
    protected String tenantId;


    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getExpdId() {
        return expdId;
    }

    public void setExpdId(String expdId) {
        this.expdId = expdId;
    }

    public String getDelInd() {
        return delInd;
    }

    public void setDelInd(String delInd) {
        this.delInd = delInd;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
