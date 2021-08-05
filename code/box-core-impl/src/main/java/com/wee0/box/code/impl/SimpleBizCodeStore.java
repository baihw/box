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

package com.wee0.box.code.impl;

import com.wee0.box.BoxConfig;
import com.wee0.box.code.IBizCode;
import com.wee0.box.code.IBizCodeInfo;
import com.wee0.box.code.IBizCodeStore;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.PropertiesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:18
 * @Description 一个简单的业务编码数据存储对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBizCodeStore implements IBizCodeStore {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleBizCodeStore.class);

    // 存储名称
    public static final String NAME = "simpleStore";

    // 默认的资源文件
    public static final String DEF_RESOURCE = "config/biz_code.properties";

    // 数据容器
    private final Map<String, String> DATA = new ConcurrentHashMap<>(256);

    // 默认的缓存大小
    private static final int DEF_CACHE_LIMIT = 256;
    private final Map<IBizCode, IBizCodeInfo> CODEINFO_CACHE = new LinkedHashMap<IBizCode, IBizCodeInfo>(DEF_CACHE_LIMIT, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<IBizCode, IBizCodeInfo> eldest) {
            return this.size() > DEF_CACHE_LIMIT;
        }
    };

    @Override
    public void set(IBizCode bizCode, String text) {
        if (null == bizCode || null == bizCode.getCode())
            return;
        this.DATA.put(bizCode.getCode(), text);
    }

    @Override
    public boolean has(IBizCode bizCode) {
        if (null == bizCode || null == bizCode.getCode())
            return false;
        return this.DATA.containsKey(bizCode.getCode());
    }

    @Override
    public String get(IBizCode bizCode) {
        if (null == bizCode || null == bizCode.getCode())
            return null;
        return this.DATA.get(bizCode.getCode());
    }

    @Override
    public IBizCodeInfo getCodeInfo(IBizCode bizCode, String... args) {
        if (null == bizCode || null == bizCode.getCode())
            throw new IllegalArgumentException("bizCode can't be null!");
        if (null == args || 0 == args.length) {
            // 缓存无参的业务编码信息
            IBizCodeInfo _bizCodeInfo = CODEINFO_CACHE.get(bizCode);
            if (null != _bizCodeInfo)
                return _bizCodeInfo;
            synchronized (CODEINFO_CACHE) {
                _bizCodeInfo = CODEINFO_CACHE.get(bizCode);
                if (null == _bizCodeInfo) {
                    String _text = this.DATA.get(bizCode.getCode());
                    _bizCodeInfo = new SimpleBizCodeInfo(bizCode, _text);
                    CODEINFO_CACHE.put(bizCode, _bizCodeInfo);
                }
                return _bizCodeInfo;
            }
        }
        String _text = this.DATA.get(bizCode.getCode());
        return new SimpleBizCodeInfo(bizCode, _text, args);
    }

    @Override
    public void loadData() {
        try (InputStream _inStream = BoxConfig.impl().getResourceAsStream(DEF_RESOURCE);) {
            Map<String, String> _propsMap = PropertiesUtils.loadToMap(_inStream);
            this.DATA.putAll(_propsMap);
            log.info("{} loaded.", DEF_RESOURCE);
        } catch (IOException e) {
            log.debug("{} not found.", DEF_RESOURCE, e);
        }
    }
}
