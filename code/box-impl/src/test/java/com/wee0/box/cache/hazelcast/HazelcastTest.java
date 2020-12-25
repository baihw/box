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

package com.wee0.box.cache.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 7:22
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class HazelcastTest {

    // 实例对象
    private static HazelcastInstance _HZ1;
    private static HazelcastInstance _HZ2;

    @BeforeClass
    public static void _before() {
        _HZ1 = Hazelcast.newHazelcastInstance();
        _HZ2 = Hazelcast.newHazelcastInstance();
    }

    @AfterClass
    public static void _after() {
        _HZ1.shutdown();
        _HZ2.shutdown();
    }

    @Test
    public void testMap() {
        IMap<String, Object> _m1 = _HZ1.getMap("m1");
        _m1.put("aString", "stringA");
        _m1.put("bTrue", true);
        _m1.put("bFalse", false);
        _m1.put("cInt1", 1);
        _m1.put("cInt2", 2);
        System.out.println("_m1:" + _m1);
        System.out.println("_m1.name:" + _m1.getName());
        System.out.println("_m1.serviceName:" + _m1.getServiceName());
        System.out.println("_m1.partitionKey:" + _m1.getPartitionKey());

        IMap<String, Object> _m2 = _HZ2.getMap("m1");
        System.out.println("_m2:" + _m2);
        Assert.assertTrue(_m2.containsKey("bTrue"));
        Assert.assertTrue(_m2.containsKey("bFalse"));
        Assert.assertFalse(_m2.containsKey("BFalse"));

//        Predicate _p1 = Predicates.in("__key", "cInt1", "cInt2");
        Set<String> _keys = new HashSet<>(2);
        _keys.add("cInt1");
        _keys.add("cInt2");
        Predicate _p1 = Predicates.in("__key", _keys.toArray(new String[]{}));
        _m2.removeAll(_p1);
        System.out.println("_m2:" + _m2);

        _m2.removeAll(Predicates.alwaysTrue());
        System.out.println("_m2:" + _m2);
        Assert.assertFalse(_m2.containsKey("bTrue"));
        Assert.assertFalse(_m2.containsKey("bFalse"));
        Assert.assertFalse(_m2.containsKey("BFalse"));
        Assert.assertEquals(0, _m2.size());
        Assert.assertEquals(0, _m1.size());
    }

}
