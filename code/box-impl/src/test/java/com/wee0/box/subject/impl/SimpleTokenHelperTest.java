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

package com.wee0.box.subject.impl;

import com.wee0.box.subject.ITokenHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/16 7:16
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleTokenHelperTest {

    // 默认使用的密钥
    static final String _DEF_SECRET = "1234567x";

    @Test
    public void encode() {
        Map<String, Object> _data = new HashMap<>();
        _data.put("userId", "0a19ba58e8ab11e9b3700242ac12010a");
//        _data.put("createTime", System.currentTimeMillis());
        _data.put("createTime", "1589676648982");
        _data.put("expireTime", 1589676648982L + 30000);
        String _token = SimpleTokenHelper.me().encode(_data, _DEF_SECRET, ITokenHelper.ALGORITHM_DES);
//        System.out.println("_token: " + _token);
    }

    @Test
    public void decode() {
        String _token = "842AA2B81A9413674A1BDFDD0D5E7E2CF903996B287FC9339847559FA15951C71B7EBA68D74345B897301B8D3B2281B21770ECF2FA7321B797D7C598597BEC484F58EAE3400DFD1E66662017377BADD1EAF8C3515437C4555F96CC0FF588445D12372F5DE2DD401B";
        Map<String, Object> _data = SimpleTokenHelper.me().decode(_token, _DEF_SECRET, ITokenHelper.ALGORITHM_DES);
        Assert.assertEquals(3, _data.size());
        Assert.assertEquals("0a19ba58e8ab11e9b3700242ac12010a", _data.get("userId"));
        Assert.assertEquals("1589676648982", _data.get("createTime"));
        Assert.assertEquals(1589676678982L, _data.get("expireTime"));
    }
}