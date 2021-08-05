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

import com.wee0.box.i18n.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:33
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class I18nBizCodeStoreTest {

    // 实例对象
    private I18nBizCodeStore store = new I18nBizCodeStore();

    @Test
    public void testGetLanguageResource() {
        Assertions.assertEquals("config/biz_code_en.properties", store.getLanguageResource(Language.en));
        Assertions.assertEquals("config/biz_code_zh_CN.properties", store.getLanguageResource(Language.zh_CN));
        Assertions.assertEquals("config/biz_code_zh_TW.properties", I18nBizCodeStore.getLanguageResource(Language.zh_TW));
    }

    @Test
    public void testHas() {
        store.has(null);
    }

}
