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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:36
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Disabled("for inheritance")
public class IStringUtilsTest {
    // 实现类实例
    protected static IStringUtils stringUtils;

    @Test
    public final void testIsEmpty() {
        String _str = null;
        Assertions.assertTrue(stringUtils.isEmpty(_str));
        Assertions.assertTrue(stringUtils.isEmpty(null));
        Assertions.assertTrue(stringUtils.isEmpty(""));
        _str = " ";
        Assertions.assertFalse(stringUtils.isEmpty(_str));
        Assertions.assertFalse(stringUtils.isEmpty(" 	"));
    }

    @Test
    public final void testHasText() {
        String _str = null;
        Assertions.assertFalse(stringUtils.hasText(_str));
        Assertions.assertFalse(stringUtils.hasText(null));
        Assertions.assertFalse(stringUtils.hasText(""));
        Assertions.assertFalse(stringUtils.hasText(" "));
        Assertions.assertFalse(stringUtils.hasText(" 	"));
        Assertions.assertTrue(stringUtils.hasText(" 	1	 	"));
        Assertions.assertTrue(stringUtils.hasText(" 	a	 	"));
        Assertions.assertTrue(stringUtils.hasText(" 	+	 	"));
        Assertions.assertTrue(stringUtils.hasText(" 	a+1	 	"));
    }

    @Test
    public final void testTrim() {
        String _str = null;
        Assertions.assertEquals("", stringUtils.trim(_str));
        Assertions.assertEquals("", stringUtils.trim(null));
        Assertions.assertEquals("", stringUtils.trim(" "));
        Assertions.assertEquals("", stringUtils.trim(" 		"));
    }

    @Test
    public final void testTrimEmptyDef() {
        String _str = null;
        Assertions.assertEquals(null, stringUtils.trimEmptyDef(_str, null));
        Assertions.assertEquals("", stringUtils.trimEmptyDef(_str, ""));
        Assertions.assertEquals(null, stringUtils.trimEmptyDef(null, null));
        Assertions.assertEquals("", stringUtils.trimEmptyDef(null, ""));
        Assertions.assertEquals("", stringUtils.trimEmptyDef("  	 ", ""));
        Assertions.assertEquals("a", stringUtils.trimEmptyDef(" a", null));
        Assertions.assertEquals("a 1", stringUtils.trimEmptyDef(" a 1", null));
        Assertions.assertEquals("a 1", stringUtils.trimEmptyDef(" 	a 1	 ", null));
        Assertions.assertNotEquals("a1", stringUtils.trimEmptyDef(" a 1", null));
    }

    @Test
    public final void testParseString() {
        Object _obj = null;
        Assertions.assertNull(stringUtils.parseString(_obj, null));
        Assertions.assertNull(stringUtils.parseString(null, null));
        _obj = new Object();
        Assertions.assertNotNull(stringUtils.parseString(_obj, null));
        _obj = " a 	b 	1   ";
        Assertions.assertEquals("a 	b 	1", stringUtils.parseString(_obj, null));
        Assertions.assertNotEquals("a  b  1", stringUtils.parseString(_obj, null));
    }

    @Test
    public final void testParseBoolean() {
        String _str = null;
        Assertions.assertTrue(stringUtils.parseBoolean(_str, true));
        Assertions.assertTrue(stringUtils.parseBoolean(null, true));
        Assertions.assertFalse(stringUtils.parseBoolean(_str, false));
        Assertions.assertFalse(stringUtils.parseBoolean(null, false));
        Assertions.assertFalse(stringUtils.parseBoolean("", false));
        Assertions.assertFalse(stringUtils.parseBoolean(" ", false));
        Assertions.assertFalse(stringUtils.parseBoolean(" 	", false));
        Assertions.assertFalse(stringUtils.parseBoolean("0", false));
        Assertions.assertFalse(stringUtils.parseBoolean("1", false));
        Assertions.assertFalse(stringUtils.parseBoolean("Yes", false));
        Assertions.assertFalse(stringUtils.parseBoolean("YES", false));

        Assertions.assertTrue(stringUtils.parseBoolean("True", false));
        Assertions.assertTrue(stringUtils.parseBoolean("TRUE", false));
        Assertions.assertTrue(stringUtils.parseBoolean("true", false));
        Assertions.assertTrue(stringUtils.parseBoolean("true ", false));
        Assertions.assertTrue(stringUtils.parseBoolean(" true", false));
        Assertions.assertTrue(stringUtils.parseBoolean(" true	 ", false));

        Assertions.assertFalse(stringUtils.parseBoolean("False", true));
        Assertions.assertFalse(stringUtils.parseBoolean("FALSE", true));
        Assertions.assertFalse(stringUtils.parseBoolean("false", true));
        Assertions.assertFalse(stringUtils.parseBoolean(" false", true));
        Assertions.assertFalse(stringUtils.parseBoolean("false ", true));
        Assertions.assertFalse(stringUtils.parseBoolean(" false 	", true));
    }

    @Test
    public final void testParseInt() {
        String _str = null;
        Assertions.assertEquals(-1, stringUtils.parseInt(_str, -1));
        Assertions.assertEquals(-1, stringUtils.parseInt(null, -1));
        Assertions.assertEquals(-1, stringUtils.parseInt("-1", -1));
        Assertions.assertEquals(-1, stringUtils.parseInt(" 	-1 ", -1));
        Assertions.assertEquals(-1, stringUtils.parseInt("a", -1));
        Assertions.assertEquals(-1, stringUtils.parseInt("a1", -1));
        Assertions.assertEquals(-1, stringUtils.parseInt("True", -1));
        Assertions.assertEquals(0, stringUtils.parseInt("0", -1));
        Assertions.assertEquals(1, stringUtils.parseInt("1", -1));
        Assertions.assertEquals(1, stringUtils.parseInt(" 1", -1));
        Assertions.assertEquals(1, stringUtils.parseInt("1 ", -1));
        Assertions.assertEquals(1, stringUtils.parseInt(" 1 ", -1));
        Assertions.assertEquals(1, stringUtils.parseInt(" 	1 	", -1));
    }

    @Test
    public final void testParseLong() {
        String _str = null;
        Assertions.assertEquals(-1, stringUtils.parseLong(_str, -1));
        Assertions.assertEquals(-1, stringUtils.parseLong(null, -1));
        Assertions.assertEquals(-1, stringUtils.parseLong("-1", -1));
        Assertions.assertEquals(-1, stringUtils.parseLong(" 	-1 ", -1));
        Assertions.assertEquals(-1, stringUtils.parseLong("a", -1));
        Assertions.assertEquals(-1, stringUtils.parseLong("a1", -1));
        Assertions.assertEquals(-1, stringUtils.parseLong("True", -1));
        Assertions.assertEquals(0, stringUtils.parseLong("0", -1));
        Assertions.assertEquals(1, stringUtils.parseLong("1", -1));
        Assertions.assertEquals(1, stringUtils.parseLong(" 1", -1));
        Assertions.assertEquals(1, stringUtils.parseLong("1 ", -1));
        Assertions.assertEquals(1, stringUtils.parseLong(" 1 ", -1));
        Assertions.assertEquals(1, stringUtils.parseLong(" 	1 	", -1));
    }

    @Test
    public final void testParseFloat() {
        String _str = null;
        Assertions.assertEquals(-1f, stringUtils.parseFloat(_str, -1f), 0.00);
        Assertions.assertEquals(-1f, stringUtils.parseFloat(null, -1f), 0.00);
        Assertions.assertEquals(-1f, stringUtils.parseFloat("-1", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseFloat(" 	-1 ", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseFloat("a", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseFloat("a1", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseFloat("True", -1f), 0.00);
        Assertions.assertEquals(0, stringUtils.parseFloat("0", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseFloat("1", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseFloat(" 1", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseFloat("1 ", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseFloat(" 1 ", -1), 0.00);
        Assertions.assertEquals(0, stringUtils.parseFloat(".0", -1), 0.00);
        Assertions.assertEquals(0.0f, stringUtils.parseFloat(".0", -1), 0.00);
        Assertions.assertEquals(0.00f, stringUtils.parseFloat(".00", -1), 0.00);
        Assertions.assertEquals(0f, stringUtils.parseFloat("0.0", -1), 0.00);
        Assertions.assertEquals(3.14f, stringUtils.parseFloat("3.14", -1), 0.00);
        Assertions.assertEquals(3.14f, stringUtils.parseFloat("3.14f", -1), 0.00);

        Assertions.assertNotEquals(3.14, stringUtils.parseFloat("3.14f", -1), 0.00);
    }

    @Test
    public final void testParseDouble() {
        String _str = null;
        Assertions.assertEquals(-1f, stringUtils.parseDouble(_str, -1f), 0.00);
        Assertions.assertEquals(-1f, stringUtils.parseDouble(null, -1f), 0.00);
        Assertions.assertEquals(-1f, stringUtils.parseDouble("-1", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseDouble(" 	-1 ", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseDouble("a", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseDouble("a1", -1f), 0.00);
        Assertions.assertEquals(-1, stringUtils.parseDouble("True", -1f), 0.00);
        Assertions.assertEquals(0, stringUtils.parseDouble("0", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseDouble("1", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseDouble(" 1", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseDouble("1 ", -1), 0.00);
        Assertions.assertEquals(1, stringUtils.parseDouble(" 1 ", -1), 0.00);
        Assertions.assertEquals(0, stringUtils.parseDouble(".0", -1), 0.00);
        Assertions.assertEquals(0.0f, stringUtils.parseDouble(".0", -1), 0.00);
        Assertions.assertEquals(0.00f, stringUtils.parseDouble(".00", -1), 0.00);
        Assertions.assertEquals(0f, stringUtils.parseDouble("0.0", -1), 0.00);
        Assertions.assertEquals(3.14, stringUtils.parseDouble("3.14", -1), 0.00);
        Assertions.assertEquals(3.14, stringUtils.parseDouble("3.14f", -1), 0.00);
        Assertions.assertEquals(3.14, stringUtils.parseDouble("3.14d", -1), 0.00);

        Assertions.assertNotEquals(3.14, stringUtils.parseDouble("3.14a", -1), 0.00);
    }

    @Test
    public final void testParseBigDecimal() {
        String _str = null;
        BigDecimal _bd_1 = new BigDecimal(-1);
        Assertions.assertEquals(new BigDecimal(-1), stringUtils.parseBigDecimal(_str, _bd_1));
        Assertions.assertEquals(new BigDecimal(-1), stringUtils.parseBigDecimal(null, _bd_1));
        Assertions.assertEquals(_bd_1, stringUtils.parseBigDecimal(_str, _bd_1));

        Assertions.assertEquals(new BigDecimal("3.14"), stringUtils.parseBigDecimal(" 3.14", _bd_1));
        Assertions.assertEquals(new BigDecimal("3.14"), stringUtils.parseBigDecimal("3.14 ", _bd_1));
        Assertions.assertEquals(new BigDecimal("3.14"), stringUtils.parseBigDecimal(" 3.14	 ", _bd_1));
        Assertions.assertNotEquals(new BigDecimal(3.14), stringUtils.parseBigDecimal("3.14", _bd_1));
        Assertions.assertNotEquals(new BigDecimal(3.14f), stringUtils.parseBigDecimal("3.14f", _bd_1));
        Assertions.assertNotEquals(new BigDecimal(3.14d), stringUtils.parseBigDecimal("3.14d", _bd_1));

        Assertions.assertEquals(new BigDecimal(0), stringUtils.parseBigDecimal(" 0 ", _bd_1));
        Assertions.assertEquals(new BigDecimal(1), stringUtils.parseBigDecimal(" 1	", _bd_1));
        Assertions.assertEquals(new BigDecimal(315), stringUtils.parseBigDecimal("315", _bd_1));
    }

    @Test
    public final void testStartsWithIgnoreCase() {
        String _str = null;
        Assertions.assertFalse(stringUtils.startsWithIgnoreCase(_str, "prefix_"));
        Assertions.assertFalse(stringUtils.startsWithIgnoreCase(null, "prefix_"));

        Assertions.assertTrue(stringUtils.startsWithIgnoreCase("prefix_str1_suffix", "prefix_"));
        Assertions.assertTrue(stringUtils.startsWithIgnoreCase("1p_str1_suffix", "1p"));

        Assertions.assertFalse(stringUtils.startsWithIgnoreCase(" prefix_str1_suffix", "prefix_"));
        Assertions.assertFalse(stringUtils.startsWithIgnoreCase(" 	prefix_str1_suffix", "prefix_"));
        Assertions.assertFalse(stringUtils.startsWithIgnoreCase("pr_str1_suffix", "prefix_"));
    }

    @Test
    public final void testEndsWithIgnoreCase() {
        String _str = null;
        Assertions.assertFalse(stringUtils.endsWithIgnoreCase(_str, "_suffix"));
        Assertions.assertFalse(stringUtils.endsWithIgnoreCase(null, "_suffix"));

        Assertions.assertTrue(stringUtils.endsWithIgnoreCase("prefix_str1_suffix", "_suffix"));
        Assertions.assertTrue(stringUtils.endsWithIgnoreCase("prefix_str1_s1", "s1"));
        Assertions.assertTrue(stringUtils.endsWithIgnoreCase("prefix_str1_s1", "1"));

        Assertions.assertFalse(stringUtils.endsWithIgnoreCase("prefix_str1_suffix ", "_suffix"));
        Assertions.assertFalse(stringUtils.endsWithIgnoreCase("prefix_str1_s1", "s"));
    }

    @Test
    public final void testStartsWithChar() {
        String _str = null;
        Assertions.assertNull(stringUtils.startsWithChar(_str, '/'));
        Assertions.assertNull(stringUtils.startsWithChar(null, '/'));

        Assertions.assertEquals("/api/test", stringUtils.startsWithChar("api/test", '/'));
        Assertions.assertEquals("/api/test", stringUtils.startsWithChar("/api/test", '/'));
        Assertions.assertEquals("/", stringUtils.startsWithChar("/", '/'));
        Assertions.assertEquals("/", stringUtils.startsWithChar("", '/'));
    }

    @Test
    public final void testEndsWithChar() {
        String _str = null;
        Assertions.assertNull(stringUtils.endsWithChar(_str, ','));
        Assertions.assertNull(stringUtils.endsWithChar(null, ','));

        Assertions.assertEquals("/a,/b,", stringUtils.endsWithChar("/a,/b", ','));
        Assertions.assertEquals("/a,/b,", stringUtils.endsWithChar("/a,/b,", ','));
        Assertions.assertEquals(",", stringUtils.endsWithChar(",", ','));
        Assertions.assertEquals(",", stringUtils.endsWithChar("", ','));
    }

    @Test
    public final void testStartsWithoutChar() {
        String _str = null;
        Assertions.assertNull(stringUtils.startsWithoutChar(_str, '/'));
        Assertions.assertNull(stringUtils.startsWithoutChar(null, '/'));

        Assertions.assertEquals("api/test", stringUtils.startsWithoutChar("api/test", '/'));
        Assertions.assertEquals("api/test", stringUtils.startsWithoutChar("/api/test", '/'));
        Assertions.assertEquals("", stringUtils.startsWithoutChar("/", '/'));
        Assertions.assertEquals("", stringUtils.startsWithoutChar("", '/'));
    }

    @Test
    public final void testEndsWithoutChar() {
        String _str = null;
        Assertions.assertNull(stringUtils.endsWithoutChar(_str, ','));
        Assertions.assertNull(stringUtils.endsWithoutChar(null, ','));

        Assertions.assertEquals("/a,/b", stringUtils.endsWithoutChar("/a,/b", ','));
        Assertions.assertEquals("/a,/b", stringUtils.endsWithoutChar("/a,/b,", ','));
        Assertions.assertEquals("", stringUtils.endsWithoutChar(",", ','));
        Assertions.assertEquals("", stringUtils.endsWithoutChar("", ','));
    }

    @Test
    public final void testCapitalize() {
        String _str = null;
        Assertions.assertEquals(null, stringUtils.capitalize(_str));
        Assertions.assertEquals(_str, stringUtils.capitalize(null));

        Assertions.assertEquals("First", stringUtils.capitalize("first"));
        Assertions.assertEquals("First", stringUtils.capitalize("First"));
        Assertions.assertEquals("FIrst", stringUtils.capitalize("fIrst"));
        Assertions.assertEquals("FIrst", stringUtils.capitalize("FIrst"));

        Assertions.assertNotEquals("First", stringUtils.capitalize(" first"));
    }

    @Test
    public final void testUnCapitalize() {
        String _str = null;
        Assertions.assertEquals(null, stringUtils.unCapitalize(_str));
        Assertions.assertEquals(_str, stringUtils.unCapitalize(null));

        Assertions.assertEquals("first", stringUtils.unCapitalize("first"));
        Assertions.assertEquals("first", stringUtils.unCapitalize("First"));
        Assertions.assertEquals("fIrst", stringUtils.unCapitalize("fIrst"));
        Assertions.assertEquals("fIrst", stringUtils.unCapitalize("FIrst"));

        Assertions.assertNotEquals("first", stringUtils.unCapitalize(" First"));
    }

    @Test
    public final void testUnderscore() {
        Assertions.assertNotNull(stringUtils.underscore(null));
        Assertions.assertEquals("test", stringUtils.underscore("test"));
        Assertions.assertEquals("test", stringUtils.underscore("Test"));
        Assertions.assertEquals("t", stringUtils.underscore("t"));
        Assertions.assertNotEquals("_t", stringUtils.underscore("T"));
        Assertions.assertEquals("test_string", stringUtils.underscore("TestString"));
        Assertions.assertEquals("test_a_b_c", stringUtils.underscore("TestABC"));
        Assertions.assertEquals("i_test", stringUtils.underscore("ITest"));
    }

    @Test
    public final void testUpperCase() {
        String _str = null;
        Assertions.assertEquals(_str, stringUtils.upperCase(null));
        Assertions.assertEquals(null, stringUtils.upperCase(_str));
        Assertions.assertEquals("TEST", stringUtils.upperCase("test"));
        Assertions.assertNotEquals("TEST", stringUtils.upperCase(" test"));
    }

    @Test
    public final void testLowerCase() {
        String _str = null;
        stringUtils.lowerCase(_str);
        Assertions.assertEquals(_str, stringUtils.lowerCase(null));
        Assertions.assertEquals(null, stringUtils.lowerCase(_str));
        Assertions.assertEquals("test", stringUtils.lowerCase("TEST"));
        Assertions.assertNotEquals("test", stringUtils.lowerCase(" TEST "));
    }

    @Test
    public final void testReplace() {
        String _str = null;
        Assertions.assertEquals(null, stringUtils.replace(_str, null, null));
        Assertions.assertEquals(_str, stringUtils.replace(_str, "", ""));
        Assertions.assertEquals(_str, stringUtils.replace(_str, "a", "b"));

        _str = " Hello, This is test 	str.";
        Assertions.assertEquals(" Hello, This is test 	str.", stringUtils.replace(_str, "", "-"));
        Assertions.assertEquals("-Hello,-This-is-test-	str.", stringUtils.replace(_str, " ", "-"));
        Assertions.assertEquals("Hello,Thisistest	str.", stringUtils.replace(_str, " ", ""));
        Assertions.assertEquals(" Hello, Thxx xx test 	str.", stringUtils.replace(_str, "is", "xx"));
        Assertions.assertEquals(" Hello, Th  test 	str.", stringUtils.replace(_str, "is", ""));
        Assertions.assertEquals(" Hello, This is ?es? 	s?r.", stringUtils.replace(_str, "t", "?"));
        Assertions.assertNotEquals(" Hello, ?his is ?es? 	s?r.", stringUtils.replace(_str, "t", "?"));
    }

    @Test
    public final void testDelete() {
        String _str = null;
        Assertions.assertEquals(null, stringUtils.delete(_str, null));
        Assertions.assertEquals(_str, stringUtils.delete(_str, ""));
        Assertions.assertEquals(_str, stringUtils.delete(_str, "x"));

        _str = " Hello, This is test 	str.";
        Assertions.assertEquals(" Hello, This is test 	str.", stringUtils.delete(_str, null));
        Assertions.assertEquals(" Hello, This is test 	str.", stringUtils.delete(_str, ""));
        Assertions.assertEquals("Hello,Thisistest	str.", stringUtils.delete(_str, " "));
        Assertions.assertEquals(" Hello, This is test str.", stringUtils.delete(_str, "	"));
        Assertions.assertEquals(" Hello, Th  test 	str.", stringUtils.delete(_str, "is"));
        Assertions.assertEquals(" Hello, This is es 	sr.", stringUtils.delete(_str, "t"));
        Assertions.assertNotEquals(" Hello, his is es 	sr.", stringUtils.delete(_str, "t"));
    }

    @Test
    public final void testDeleteAny() {
        String _str = null;
        Assertions.assertEquals(null, stringUtils.deleteAny(_str, null));
        Assertions.assertEquals(_str, stringUtils.deleteAny(_str, ""));
        Assertions.assertEquals(null, stringUtils.deleteAny(_str, "x"));

        _str = " Hello, This is test 	str.";
        Assertions.assertEquals(" Hello, This is test 	str.", stringUtils.deleteAny(_str, null));
        Assertions.assertEquals(" Hello, This is test 	str.", stringUtils.deleteAny(_str, ""));
        Assertions.assertEquals(" Hello, Thi i e 	r.", stringUtils.deleteAny(_str, "sbt"));
        Assertions.assertEquals(" Hello, Th  tet 	tr.", stringUtils.deleteAny(_str, "is"));
        Assertions.assertEquals(" Hllo, Thi i tt 	tr.", stringUtils.deleteAny(_str, "es"));
    }
}
