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

import com.wee0.box.testObjects.Level1Obj;
import com.wee0.box.testObjects.Level2Obj;
import com.wee0.box.testObjects.Level3Obj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:31
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Disabled("for inheritance")
public class IJsonUtilsTest {

    // 实现类实例
    protected static IJsonUtils impl;

    private static final String JSON_STRING1;
    private static final String JSON_STRING2;
    private static final Level1Obj LEVEL1_OBJ1;
    private static final Level2Obj LEVEL2_OBJ1;
    private static final Level3Obj LEVEL3_OBJ1;

    static {
        StringBuilder _sb = new StringBuilder();
        _sb.append("{\"privateString1\":\"privateString1\"");
        _sb.append(",\"string1\":\"string1\"");
        _sb.append(",\"int1\":100,\"float1\":2.1,\"bool1\":true");
        _sb.append(",\"date1\":\"2019-08-15 00:00:00\"");
        _sb.append(",\"map1\":{\"d-2.1\":-2.1,\"s1\":\"s1v\",\"bFalse\":false,\"d2.1\":2.1,\"bTrue\":true,\"i1\":1}");
        _sb.append(",\"list1\":[\"string1\",\"string2\",\"string3\"]");
        _sb.append(",\"listMap1\":[{\"d-2.1\":-2.1,\"s1\":\"s1v\",\"bFalse\":false,\"d2.1\":2.1,\"bTrue\":true,\"i1\":1},{\"name\":\"map2\"}]}");
        JSON_STRING1 = _sb.toString();

        _sb.delete(0, _sb.length());
        _sb.append("{'privateString1':'hello, ','string1':'World!中文', \"extKey1\": 'extKey1Value'}");
        JSON_STRING2 = _sb.toString();

        Map<String, Object> _map1 = new HashMap<>();
        _map1.put("s1", "s1v");
        _map1.put("bTrue", true);
        _map1.put("bFalse", false);
        _map1.put("i1", 1);
//        float类型丢失，会被识别为double，避免使用。
//        _map1.put("f2.1", 2.1f);
//        _map1.put("f-2.1", -2.1f);
//        BigDecimal类型丢失，会被识别为double，避免使用。
//        _map1.put("bd1.9", new BigDecimal("1.9"));
        _map1.put("d2.1", 2.1);
        _map1.put("d-2.1", -2.1);
        Map<String, Object> _map2 = new HashMap<>();
        _map2.put("name", "map2");
        List<Map<String, Object>> _listMap1 = new ArrayList<>();
        _listMap1.add(_map1);
        _listMap1.add(_map2);
        List<String> _list1 = new ArrayList<>();
        _list1.add("string1");
        _list1.add("string2");
        _list1.add("string3");
        LEVEL1_OBJ1 = new Level1Obj();
        LEVEL1_OBJ1.setString1("levelString1");
        LEVEL1_OBJ1.setMap1(_map1);
        LEVEL1_OBJ1.setList1(_list1);
        LEVEL1_OBJ1.setListMap1(_listMap1);

        LEVEL2_OBJ1 = new Level2Obj();
        LEVEL2_OBJ1.setString1("level_level2_string1");
        LEVEL2_OBJ1.setMap1(_map1);
        LEVEL2_OBJ1.setList1(_list1);
        LEVEL2_OBJ1.setListMap1(_listMap1);
        Level3Obj _level3Obj = new Level3Obj();
        _level3Obj.setString1("level2_level3_string1");
        LEVEL2_OBJ1.setLevel3Obj(_level3Obj);
        LEVEL1_OBJ1.setLevel2Obj(LEVEL2_OBJ1);

        LEVEL3_OBJ1 = new Level3Obj();
        LEVEL3_OBJ1.setBool1(true);
//        LEVEL3_OBJ1.setDate1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-08-15 00:00:00"));
        Calendar _calendar = Calendar.getInstance();
        _calendar.set(2019, 7, 15, 0, 0, 0);
        _calendar.set(Calendar.MILLISECOND, 0);
        LEVEL3_OBJ1.setDate1(_calendar.getTime());
        LEVEL3_OBJ1.setFloat1(2.1f);
        LEVEL3_OBJ1.setInt1(100);
        LEVEL3_OBJ1.setString1("string1");
        LEVEL3_OBJ1.setPrivateString1("privateString1");
        LEVEL3_OBJ1.setMap1(_map1);
        LEVEL3_OBJ1.setList1(_list1);
        LEVEL3_OBJ1.setListMap1(_listMap1);
        LEVEL1_OBJ1.setLevel3Obj(LEVEL3_OBJ1);
    }

    @Test
    public void test1() {

        String _str1 = impl.writeToString(LEVEL1_OBJ1);
        System.out.println("--------str1-----------");
        System.out.println(_str1);
        System.out.println("--------str1-----------");
        Level1Obj _level1Obj = impl.readToObject(_str1, Level1Obj.class);
        System.out.println("level1Obj:" + _level1Obj);
        Assertions.assertEquals(LEVEL1_OBJ1, _level1Obj);

        String _str2 = impl.writeToString(LEVEL2_OBJ1);
        System.out.println("--------str2-----------");
        System.out.println(_str2);
        System.out.println("--------str2-----------");
        Level2Obj _level2Obj = impl.readToObject(_str2, Level2Obj.class);
        System.out.println("level2Obj:" + _level2Obj);
        Assertions.assertEquals(LEVEL2_OBJ1, _level2Obj);

        String _str3 = impl.writeToString(LEVEL3_OBJ1);
        System.out.println("--------str3-----------");
        System.out.println(_str3);
        System.out.println("--------str3-----------");
        Level3Obj _level3Obj = impl.readToObject(_str3, Level3Obj.class);
        System.out.println("level3Obj:" + _level3Obj);
        Assertions.assertEquals(LEVEL3_OBJ1, _level3Obj);

        Assertions.assertEquals(LEVEL3_OBJ1, impl.readToObject(JSON_STRING1, Level3Obj.class));
        Level3Obj _level3Obj2 = new Level3Obj();
        _level3Obj2.setPrivateString1("hello, ");
        _level3Obj2.setString1("World!中文");
        Assertions.assertEquals(_level3Obj2, impl.readToObject(JSON_STRING2, Level3Obj.class));
    }

}
