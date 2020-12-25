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

package com.wee0.box.cache.caffeine;

import com.wee0.box.util.shortcut.CheckUtils;

import java.time.Instant;
import java.time.temporal.ChronoField;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 8:12
 * @Description 缓存元素对象
 * <pre>
 * 补充说明
 * </pre>
 **/
final class CaffeineCacheItem {

    // 缓存对象
    private final Object OBJ;
    // 缓存对象创建时间：精确到秒
    private long createTime;
    // 缓存对象过期时间：多少秒后过期
    private long expire;

    /**
     * 使用指定参数构造对象
     *
     * @param obj    缓存对象
     * @param expire 过期时间，单位：秒。
     */
    public CaffeineCacheItem(Object obj, long expire) {
        this.OBJ = CheckUtils.checkNotNull(obj, "obj can not be null!");
        this.createTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
        this.expire = expire;
    }

    /**
     * @return 缓存数据
     */
    public Object value() {
        return this.OBJ;
    }

    /**
     * @return 缓存数据过期时间
     */
    public long getExpire() {
        return this.expire;
    }

    /**
     * 重设过期时间。
     *
     * @param expire 过期时间
     */
    public void updateExpire(long expire) {
        // 获取当前秒数作为当前缓存元素的创建时间
        this.createTime = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
        // 设置新的过期时间
        this.expire = expire;
    }

    /**
     * 更新过期时间。
     */
    public void updateExpire() {
        // 获取当前秒数
        final long _CURR_SEC = Instant.now().getLong(ChronoField.INSTANT_SECONDS);
        // 计算从创建到现在已经过去的秒数
        long diff = _CURR_SEC - createTime;
        // 更新过期时间扣除已经过去的秒数
        this.expire = this.expire - diff;
    }

    @Override
    public String toString() {
        return this.OBJ.toString();
    }

    @Override
    public int hashCode() {
        return this.OBJ.hashCode();
    }

}
