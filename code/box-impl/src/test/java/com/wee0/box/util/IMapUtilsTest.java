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

package com.wee0.box.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/23 7:02
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for inheritance")
public class IMapUtilsTest {
    // 实现类实例
    protected static IMapUtils impl;

    private static final Map<String, Object> _MAP1 = new HashMap<>(6);

    static {
        _MAP1.put("k1", "v1");
        _MAP1.put("k2", "v2");
        _MAP1.put("k3", "v3");
        _MAP1.put("k4", "v4");
        _MAP1.put("k5", "v5");
        _MAP1.put("k6", "v6");
    }

    @Test
    public void remove() {
        Map<String, String> _m1 = new HashMap<>();
        _m1.put("k1", "v1");
        _m1.put("k2", "v2");
        _m1.put("k3", "v3");
        _m1.put("k4", "v4");
        _m1.put("k5", "v5");
        _m1.put("k6", "v6");
        impl.remove(_m1, "k2", "k3");
        Assert.assertEquals(4, _m1.size());
    }

    @Test
    public void keep() {
        impl.keep(_MAP1, "k1", "k6");
        Assert.assertEquals(2, _MAP1.size());
    }
}
