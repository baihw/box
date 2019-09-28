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
import com.wee0.box.code.BizCodeDef;
import com.wee0.box.testObjects.BizCodeExt1;
import com.wee0.box.code.IBizCode;
import com.wee0.box.code.IBizCodeInfo;
import com.wee0.box.i18n.Language;
import com.wee0.box.i18n.Locale;
import com.wee0.box.impl.BoxConfigKeys;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:32
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBizCodeManagerTest {

    // 实例对象
    private static SimpleBizCodeManager bizCodeManager = SimpleBizCodeManager.me();

    // 临时编码
    private static final IBizCode TMP_CODE1 = new IBizCode() {
        @Override
        public String getCode() {
            return "TMP001";
        }
    };

    @BeforeClass
    public static void init() {
        bizCodeManager.addBizCodeEnum(BizCodeDef.class);
        bizCodeManager.addBizCodeEnum(BizCodeExt1.class);
//        bizCodeManager.addBizCodeInitializer(BizCodeExt1.Ext1Test1);
        bizCodeManager.init();
    }

    @Test
    public void testGetCodeInfo() {

        IBizCodeInfo _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test1);
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("系统异常，请跟管理员联系", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系", _bizCodeInfo.formatText());

        _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test2, "0", "1", "2");
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("系统异常，请跟管理员联系。p0:{0}，p1:{1}，p2:{2}。", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系。p0:0，p1:1，p2:2。", _bizCodeInfo.formatText());
        Assert.assertEquals("系统异常，请跟管理员联系。p0:2，p1:1，p2:0。", _bizCodeInfo.formatText("2", "1", "0"));
        Assert.assertEquals("系统异常，请跟管理员联系。p0:2，p1:{1}，p2:{2}。", _bizCodeInfo.formatText("2"));

        _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test3, "0", "1", "2");
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("系统异常，请跟管理员联系。p2:{2}，p1:{1}，p0:{0}。", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系。p2:2，p1:1，p0:0。", _bizCodeInfo.formatText());
        Assert.assertEquals("系统异常，请跟管理员联系。p2:0，p1:1，p0:2。", _bizCodeInfo.formatText("2", "1", "0"));

        _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test4, "0", "1", "2");
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("系统异常，请跟管理员联系。p0:{0}，p1:{1}，p0:{0}。", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系。p0:0，p1:1，p0:0。", _bizCodeInfo.formatText());
        Assert.assertEquals("系统异常，请跟管理员联系。p0:2，p1:1，p0:2。", _bizCodeInfo.formatText("2", "1", "0"));

        _bizCodeInfo = bizCodeManager.getCodeInfo(TMP_CODE1);
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertNull(_bizCodeInfo.getText());
        Assert.assertNull(_bizCodeInfo.formatText());
        Assert.assertNull(_bizCodeInfo.formatText("2", "1", "0"));
    }

    @Test
    public void testGetCodeInfoI8n() {
        if (!I18nBizCodeStore.NAME.equals(BoxConfig.impl().get(BoxConfigKeys.bizCodeStore))) {
            // 如果没有启用i18nStore，则不执行此测试用例。
            return;
        }

        IBizCodeInfo _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test1);
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("系统异常，请跟管理员联系", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系", _bizCodeInfo.formatText());

        Locale.impl().setLanguage(Language.en);
        _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test2, "0", "1", "2");
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("system exception, please contact the administrator.p0:{0}，p1:{1}，p2:{2}。", _bizCodeInfo.getText());
        Assert.assertEquals("system exception, please contact the administrator.p0:0，p1:1，p2:2。", _bizCodeInfo.formatText());

        Locale.impl().setLanguage(Language.zh_TW);
        _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test3, "0", "1", "2");
        Assert.assertNotNull(_bizCodeInfo);
        // 因为zh_TW配置信息为空，所以取默认值
        Assert.assertEquals("系统异常，请跟管理员联系。p2:{2}，p1:{1}，p0:{0}。", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系。p2:2，p1:1，p0:0。", _bizCodeInfo.formatText());

        Locale.impl().setLanguage(Language.zh_CN);
        _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test2, "0", "1", "2");
        Assert.assertNotNull(_bizCodeInfo);
        Assert.assertEquals("系统异常，请跟管理员联系。p0:{0}，p1:{1}，p2:{2}。", _bizCodeInfo.getText());
        Assert.assertEquals("系统异常，请跟管理员联系。p0:0，p1:1，p2:2。", _bizCodeInfo.formatText());

    }

    @Test
    public void testGetCodeInfoI8nThread() {
        if (!I18nBizCodeStore.NAME.equals(BoxConfig.impl().get(BoxConfigKeys.bizCodeStore))) {
            // 如果没有启用i18nStore，则不执行此测试用例。
            return;
        }
        final CountDownLatch _LATCH = new CountDownLatch(3);
        Thread _thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(Language.zh_CN, Locale.impl().getLanguage());
                Locale.impl().setLanguage(Language.en);
                Assert.assertEquals(Language.en, Locale.impl().getLanguage());

                IBizCodeInfo _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test2, "0", "1", "2");
                Assert.assertNotNull(_bizCodeInfo);
                Assert.assertEquals("system exception, please contact the administrator.p0:{0}，p1:{1}，p2:{2}。", _bizCodeInfo.getText());
                Assert.assertEquals("system exception, please contact the administrator.p0:0，p1:1，p2:2。", _bizCodeInfo.formatText());

                _LATCH.countDown();
                Assert.assertEquals(Language.en, Locale.impl().getLanguage());
                Locale.impl().setLanguage(null);
            }
        }, "英文测试");

        Thread _thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(Language.zh_CN, Locale.impl().getLanguage());
                Locale.impl().setLanguage(Language.zh_TW);
                Assert.assertEquals(Language.zh_TW, Locale.impl().getLanguage());

                IBizCodeInfo _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test3, "0", "1", "2");
                Assert.assertNotNull(_bizCodeInfo);
                // 因为zh_TW配置信息为空，所以取默认值
                Assert.assertEquals("系统异常，请跟管理员联系。p2:{2}，p1:{1}，p0:{0}。", _bizCodeInfo.getText());
                Assert.assertEquals("系统异常，请跟管理员联系。p2:2，p1:1，p0:0。", _bizCodeInfo.formatText());

                _LATCH.countDown();
                Assert.assertEquals(Language.zh_TW, Locale.impl().getLanguage());
                Locale.impl().setLanguage(null);
            }
        }, "中文繁体测试");

        Thread _thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(Language.zh_CN, Locale.impl().getLanguage());
                Locale.impl().setLanguage(Language.zh_CN);

                IBizCodeInfo _bizCodeInfo = bizCodeManager.getCodeInfo(BizCodeExt1.Ext1Test2, "0", "1", "2");
                Assert.assertNotNull(_bizCodeInfo);
                Assert.assertEquals("系统异常，请跟管理员联系。p0:{0}，p1:{1}，p2:{2}。", _bizCodeInfo.getText());
                Assert.assertEquals("系统异常，请跟管理员联系。p0:0，p1:1，p2:2。", _bizCodeInfo.formatText());

                _LATCH.countDown();
                Assert.assertEquals(Language.zh_CN, Locale.impl().getLanguage());
                Locale.impl().setLanguage(null);
            }
        }, "中文简体测试");

        _thread1.start();
        _thread2.start();
        _thread3.start();
        try {
            _LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test(expected = BoxRuntimeException.class)
//    public void testEx01() {
//        bizCodeManager.addBizCodeEnum(BizCodeExt2.class);
//        bizCodeManager.init();
//    }

}
