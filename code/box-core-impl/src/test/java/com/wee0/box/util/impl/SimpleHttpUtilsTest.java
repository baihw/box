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

package com.wee0.box.util.impl;

import com.wee0.box.util.IHttpUtils;
import com.wee0.box.util.IHttpUtilsTest;
import com.wee0.box.util.shortcut.HttpUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/15 7:52
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleHttpUtilsTest extends IHttpUtilsTest {

    @BeforeAll
    public static void setup() {
        impl = SimpleHttpUtils.me();
    }

    @Test
    public void testGet() {
        IHttpUtils.IHttpResult _result = impl.httpGet("https://cn.bing.com");
        System.out.println("result:" + _result);
    }

    @Test
    public void testFormUpload() {
//        File _file = new File("/D/test/test.data");
        File _file = new File("F:\\win10\\cn_windows_10_enterprise_ltsc_2019_x64_dvd_9c09ff24.iso");
        String _url = "http://192.168.1.204:9000/api/up/upload2";
        Map<String, Object> _params = new HashMap<String, Object>();
        _params.put("medicalId", "123321");
        _params.put("file1", _file);

        IHttpUtils.IHttpResult _r = HttpUtils.impl().formUpload(_url, _params);
        System.out.println("_r:" + _r);
    }

}
