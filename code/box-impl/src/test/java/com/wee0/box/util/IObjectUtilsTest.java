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

import com.wee0.box.testObjects.Level3Obj;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/16 13:58
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for inheritance")
public class IObjectUtilsTest {

    // 实现类实例
    protected static IObjectUtils impl;

    private static final Level3Obj LEVEL3_OBJ1;

    static {
        LEVEL3_OBJ1 = new Level3Obj();
        LEVEL3_OBJ1.setBool1(true);
        Calendar _calendar = Calendar.getInstance();
        _calendar.set(2019, 11, 16, 0, 0, 0);
        _calendar.set(Calendar.MILLISECOND, 0);
        LEVEL3_OBJ1.setDate1(_calendar.getTime());
        LEVEL3_OBJ1.setFloat1(2.1f);
        LEVEL3_OBJ1.setInt1(100);
        LEVEL3_OBJ1.setString1("string1");
        LEVEL3_OBJ1.setPrivateString1("privateString1");
        Map<String, Object> _map1 = new HashMap<>();
        _map1.put("s1", "s1v");
        _map1.put("bTrue", true);
        _map1.put("bFalse", false);
        _map1.put("i1", 1);
        LEVEL3_OBJ1.setMap1(_map1);
        List<String> _list1 = new ArrayList<>();
        _list1.add("string1");
        _list1.add("string2");
        _list1.add("string3");
        LEVEL3_OBJ1.setList1(_list1);
        Map<String, Object> _map2 = new HashMap<>();
        _map2.put("name", "map2");
        List<Map<String, Object>> _listMap1 = new ArrayList<>();
        _listMap1.add(_map1);
        _listMap1.add(_map2);
        LEVEL3_OBJ1.setListMap1(_listMap1);
    }

    @Test
    public void setProperty() {
        Level3Obj _obj = new Level3Obj();
        impl.setProperty(_obj, "string1", "str1");
        Assert.assertEquals("str1", _obj.getString1());
    }

    @Test
    public void setProperties() {
        Level3Obj _obj = new Level3Obj();

        Map<String, Object> _data = new HashMap<>(32);
        _data.put("string1", "s1");
        _data.put("bool1", true);
        _data.put("int1", 11);
        _data.put("float1", 21F);
        _data.put("privateString1", "private1");
//        _data.put("PrivateString1", "private1");
        Map<String, Object> _map1 = new HashMap<>();
        _map1.put("s1", "s1v");
        _map1.put("s2", "s2v");
        _data.put("map1", _map1);

        impl.setProperties(_obj, _data);

        Assert.assertEquals("s1", _obj.getString1());
        Assert.assertTrue(_obj.isBool1());
        Assert.assertEquals(11, _obj.getInt1());
        Assert.assertEquals(21F, _obj.getFloat1(), 0);
        Assert.assertEquals("private1", _obj.getPrivateString1());

        Assert.assertNotNull(_obj.getMap1());
        Assert.assertEquals(2, _obj.getMap1().size());
        Assert.assertEquals("s1v", _obj.getMap1().get("s1"));
        Assert.assertEquals("s2v", _obj.getMap1().get("s2"));
    }

//    @Test
//    public void setProperties1(){
//        Level3Obj _obj = new Level3Obj();
//        impl.setProperties(_obj, _DATA1);
//        Assert.assertEquals(LEVEL3_OBJ1, _obj);
//    }

//    @Test
//    public void toMap() {
//        System.out.println(impl.toMap(LEVEL3_OBJ1));
//    }

}
