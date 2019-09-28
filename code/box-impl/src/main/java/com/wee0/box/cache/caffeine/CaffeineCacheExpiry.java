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

import com.github.benmanes.caffeine.cache.Expiry;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 8:12
 * @Description 缓存过期处理
 * <pre>
 * 补充说明
 * </pre>
 **/
final class CaffeineCacheExpiry implements Expiry<String, CaffeineCacheItem> {

    @Override
    public long expireAfterCreate(String key, CaffeineCacheItem value, long currentTime) {
        // 元素被创建后，设置有效时间。
        return _expireConvert(value.getExpire());
    }

    @Override
    public long expireAfterUpdate(String key, CaffeineCacheItem value, long currentTime, long currentDuration) {
        // 元素被修改后，重置有效时间。
        return _expireConvert(value.getExpire());
//        long expireTime = currentTime + value.getExpire();
//        System.out.println("afterUpdate... key:" + key + ", time:" + value.getExpire() + ", currTime:" + currentTime + ", duration:" + currentDuration + ", expire:" + expireTime);
//        return expireTime;
    }

    @Override
    public long expireAfterRead(String key, CaffeineCacheItem value, long currentTime, long currentDuration) {
        // 元素被访问后，重置有效时间。
        return _expireConvert(value.getExpire());
    }

    /**
     * 缓存过期时间转换方法：Caffeine使用的是纳秒，我们用的是秒;Caffeine中的0表示立即删除，我们表示永不过期。
     *
     * @param expire 平台规范的过期时间
     * @return 转换后的过期时间
     */
    private static long _expireConvert(long expire) {
        if (0 == expire) {
            return Long.MAX_VALUE;
        } else {
            // Caffeine使用的是纳秒时间，而我们使用的是秒，所以要*1000*1000*1000。
            // 如果1_000_000_000不加'l'如：1000_000_000 * 2000 会导致整形溢出。
            return 1000_000_000l * expire;
        }
    }

}
