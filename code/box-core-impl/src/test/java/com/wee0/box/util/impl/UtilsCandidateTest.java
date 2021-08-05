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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 8:22
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class UtilsCandidateTest {

    @Test
    public void createFifoCache() {
        final int _cacheLimit = 3;
        Map<String, String> _cache = UtilsCandidate.createFifoCache(_cacheLimit);

        for (int _i = 0; _i < _cacheLimit; _i++) {
            _cache.put("k" + _i, "v" + _i);
        }
        Assertions.assertTrue(_cache.containsKey("k0"));

        _cache.put("k3", "v3");
        Assertions.assertTrue(_cache.containsKey("k3"));
        Assertions.assertFalse(_cache.containsKey("k0"));

        _cache.put("k4", "v4");
        Assertions.assertTrue(_cache.containsKey("k4"));
        Assertions.assertFalse(_cache.containsKey("k1"));

        _cache.get("k2");
        _cache.put("k5", "v5");
        Assertions.assertTrue(_cache.containsKey("k3"));
        Assertions.assertFalse(_cache.containsKey("k2"));
    }

    @Test
    public void createLruCache() {
        final int _cacheLimit = 3;
        Map<String, String> _cache = UtilsCandidate.createLruCache(_cacheLimit);

        for (int _i = 0; _i < _cacheLimit; _i++) {
            _cache.put("k" + _i, "v" + _i);
        }
        Assertions.assertTrue(_cache.containsKey("k0"));

        _cache.put("k3", "v3");
        Assertions.assertTrue(_cache.containsKey("k3"));
        Assertions.assertFalse(_cache.containsKey("k0"));

        _cache.put("k4", "v4");
        Assertions.assertTrue(_cache.containsKey("k4"));
        Assertions.assertFalse(_cache.containsKey("k1"));

        _cache.get("k2");
        _cache.put("k5", "v5");
        Assertions.assertTrue(_cache.containsKey("k2"));
        Assertions.assertFalse(_cache.containsKey("k3"));
    }

    public static void main(String[] args) {
        System.out.println(Boolean.valueOf(""));
        System.out.println(Short.valueOf(""));
    }

}
