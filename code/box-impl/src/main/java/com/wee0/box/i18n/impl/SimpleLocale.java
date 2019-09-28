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

package com.wee0.box.i18n.impl;

import com.wee0.box.i18n.ILocale;
import com.wee0.box.i18n.Language;
import com.wee0.box.util.shortcut.ThreadUtils;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:12
 * @Description 一个简单的当前语言环境对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleLocale implements ILocale {

    // 数据容器
    private static final ThreadLocal<Language> DATA = ThreadUtils.createThreadLocal();

    @Override
    public void setLanguage(Language language) {
        if (null == language) {
            DATA.remove();
            return;
        }
        DATA.set(language);
    }

    @Override
    public Language getLanguage() {
        Language _language = DATA.get();
        if (null == _language) {
//            _language = getByJavaLocale();
            _language = Language.zh_CN;
        }
        return _language;
    }

    // 从当前全局环境获取语言信息
    Language getByJavaLocale() {
        java.util.Locale _javaLocale = java.util.Locale.getDefault();
        if (java.util.Locale.ENGLISH.equals(_javaLocale)) {
            return Language.en;
        }
        if (java.util.Locale.TAIWAN.equals(_javaLocale)) {
            return Language.zh_TW;
        }
        return Language.zh_CN;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleLocale() {
        if (null != SimpleLocaleHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleLocaleHolder {
        private static final SimpleLocale _INSTANCE = new SimpleLocale();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleLocaleHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleLocale me() {
        return SimpleLocaleHolder._INSTANCE;
    }

}
