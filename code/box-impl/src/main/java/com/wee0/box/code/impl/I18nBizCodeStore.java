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
import com.wee0.box.i18n.Language;
import com.wee0.box.i18n.Locale;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.PropertiesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:19
 * @Description 支持国际化的业务编码数据存储对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class I18nBizCodeStore implements IBizCodeStore {

    // 存储名称
    public static final String NAME = "i18nStore";

    // 资源文件名称模式
    public static final String PATTERN_RESOURCE = "config/biz_code_%s.properties";

    // 默认值设置文件
    private static final String defaultResource = "config/biz_code.properties";
    // 默认值数据容器。
    private final Map<String, String> defaultData = new HashMap<>(256);

    // 数据容器。
    private final Map<Language, Map<String, String>> DATA = new HashMap<>(8);

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(I18nBizCodeStore.class);

    @Override
    public boolean has(IBizCode bizCode) {
        if (null == bizCode || null == bizCode.getCode())
            return false;
        if (defaultData.containsKey(bizCode.getCode()))
            return true;
        Language _lang = Locale.impl().getLanguage();
        return DATA.containsKey(_lang) && DATA.get(_lang).containsKey(bizCode.getCode());
    }

    @Override
    public String get(IBizCode bizCode) {
        if (null == bizCode || null == bizCode.getCode())
            return null;
        Language _lang = Locale.impl().getLanguage();
        Map<String, String> _langData = this.DATA.get(_lang);
        if (null == _langData || _langData.isEmpty())
            return defaultData.get(bizCode.getCode());
        String _result = _langData.get(bizCode.getCode());
        if (null == _result)
            return defaultData.get(bizCode.getCode());
        return _result;
    }

    @Override
    public IBizCodeInfo getCodeInfo(IBizCode bizCode, String... args) {
        if (null == bizCode || null == bizCode.getCode())
            throw new IllegalArgumentException("bizCode can't be null!");
        String _text = get(bizCode);
        return new SimpleBizCodeInfo(bizCode, _text, args);
    }

    @Override
    public void loadData() {
        String _defaultFilePath = BoxConfig.impl().getResourcePath(defaultResource);
        File _defaultFile = new File(_defaultFilePath);
        if (_defaultFile.exists()) {
            Map<String, String> _propsMap = PropertiesUtils.loadToMap(_defaultFile);
            this.defaultData.putAll(_propsMap);
            log.info("{} loaded.", defaultResource);
        }

        for (Language _lang : Language.values()) {
            String _resourceName = getLanguageResource(_lang);
            String _filePath = BoxConfig.impl().getResourcePath(_resourceName);
            File _file = new File(_filePath);
            if (!_file.exists()) {
                log.debug("{} not found.", _resourceName);
                continue;
            }
            Map<String, String> _propsMap = PropertiesUtils.loadToMap(_file);
            this.DATA.put(_lang, _propsMap);
            log.info("{} loaded.", _resourceName);
        }
    }

    /**
     * 当前版本仅为设置默认值，暂不支持国际化数据的设置。
     *
     * @param bizCode 业务编码
     * @param text    文本值
     */
    @Override
    public void set(IBizCode bizCode, String text) {
        if (null == bizCode || null == bizCode.getCode())
            return;
        this.defaultData.put(bizCode.getCode(), text);
    }

    /**
     * 获取指定语言的资源文件名称
     *
     * @param language 语言
     * @return 资源文件名称
     */
    static String getLanguageResource(Language language) {
        return String.format(PATTERN_RESOURCE, language.getCode());
    }
}
