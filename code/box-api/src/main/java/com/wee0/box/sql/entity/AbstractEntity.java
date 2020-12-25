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

package com.wee0.box.sql.entity;

import com.wee0.box.util.shortcut.ObjectUtils;

import java.io.Serializable;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 8:16
 * @Description 一个可选的抽象实体基类
 * <pre>
 * 补充说明
 * </pre>
 **/
public abstract class AbstractEntity<ID> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设置标识
     *
     * @param id 标识
     */
    public abstract void setId(final ID id);

    /**
     * @return 获取标识
     */
    public abstract ID getId();

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        AbstractEntity<?> that = (AbstractEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @Override
    public String toString() {
        return ObjectUtils.impl().reflectionToString(this);
    }

}
