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

import java.math.BigDecimal;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:36
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for inheritance")
public class IStringUtilsTest {
    // 实现类实例
    protected static IStringUtils stringUtils;

    @Test
    public final void testIsEmpty() {
        String _str = null;
        Assert.assertTrue(stringUtils.isEmpty(_str));
        Assert.assertTrue(stringUtils.isEmpty(null));
        Assert.assertTrue(stringUtils.isEmpty(""));
        _str = " ";
        Assert.assertFalse(stringUtils.isEmpty(_str));
        Assert.assertFalse(stringUtils.isEmpty(" 	"));
    }

    @Test
    public final void testHasText() {
        String _str = null;
        Assert.assertFalse(stringUtils.hasText(_str));
        Assert.assertFalse(stringUtils.hasText(null));
        Assert.assertFalse(stringUtils.hasText(""));
        Assert.assertFalse(stringUtils.hasText(" "));
        Assert.assertFalse(stringUtils.hasText(" 	"));
        Assert.assertTrue(stringUtils.hasText(" 	1	 	"));
        Assert.assertTrue(stringUtils.hasText(" 	a	 	"));
        Assert.assertTrue(stringUtils.hasText(" 	+	 	"));
        Assert.assertTrue(stringUtils.hasText(" 	a+1	 	"));
    }

    @Test
    public final void testTrim() {
        String _str = null;
        Assert.assertEquals("", stringUtils.trim(_str));
        Assert.assertEquals("", stringUtils.trim(null));
        Assert.assertEquals("", stringUtils.trim(" "));
        Assert.assertEquals("", stringUtils.trim(" 		"));
    }

    @Test
    public final void testTrimEmptyDef() {
        String _str = null;
        Assert.assertEquals(null, stringUtils.trimEmptyDef(_str, null));
        Assert.assertEquals("", stringUtils.trimEmptyDef(_str, ""));
        Assert.assertEquals(null, stringUtils.trimEmptyDef(null, null));
        Assert.assertEquals("", stringUtils.trimEmptyDef(null, ""));
        Assert.assertEquals("", stringUtils.trimEmptyDef("  	 ", ""));
        Assert.assertEquals("a", stringUtils.trimEmptyDef(" a", null));
        Assert.assertEquals("a 1", stringUtils.trimEmptyDef(" a 1", null));
        Assert.assertEquals("a 1", stringUtils.trimEmptyDef(" 	a 1	 ", null));
        Assert.assertNotEquals("a1", stringUtils.trimEmptyDef(" a 1", null));
    }

    @Test
    public final void testParseString() {
        Object _obj = null;
        Assert.assertNull(stringUtils.parseString(_obj, null));
        Assert.assertNull(stringUtils.parseString(null, null));
        _obj = new Object();
        Assert.assertNotNull(stringUtils.parseString(_obj, null));
        _obj = " a 	b 	1   ";
        Assert.assertEquals("a 	b 	1", stringUtils.parseString(_obj, null));
        Assert.assertNotEquals("a  b  1", stringUtils.parseString(_obj, null));
    }

    @Test
    public final void testParseBoolean() {
        String _str = null;
        Assert.assertTrue(stringUtils.parseBoolean(_str, true));
        Assert.assertTrue(stringUtils.parseBoolean(null, true));
        Assert.assertFalse(stringUtils.parseBoolean(_str, false));
        Assert.assertFalse(stringUtils.parseBoolean(null, false));
        Assert.assertFalse(stringUtils.parseBoolean("", false));
        Assert.assertFalse(stringUtils.parseBoolean(" ", false));
        Assert.assertFalse(stringUtils.parseBoolean(" 	", false));
        Assert.assertFalse(stringUtils.parseBoolean("0", false));
        Assert.assertFalse(stringUtils.parseBoolean("1", false));
        Assert.assertFalse(stringUtils.parseBoolean("Yes", false));
        Assert.assertFalse(stringUtils.parseBoolean("YES", false));

        Assert.assertTrue(stringUtils.parseBoolean("True", false));
        Assert.assertTrue(stringUtils.parseBoolean("TRUE", false));
        Assert.assertTrue(stringUtils.parseBoolean("true", false));
        Assert.assertTrue(stringUtils.parseBoolean("true ", false));
        Assert.assertTrue(stringUtils.parseBoolean(" true", false));
        Assert.assertTrue(stringUtils.parseBoolean(" true	 ", false));

        Assert.assertFalse(stringUtils.parseBoolean("False", true));
        Assert.assertFalse(stringUtils.parseBoolean("FALSE", true));
        Assert.assertFalse(stringUtils.parseBoolean("false", true));
        Assert.assertFalse(stringUtils.parseBoolean(" false", true));
        Assert.assertFalse(stringUtils.parseBoolean("false ", true));
        Assert.assertFalse(stringUtils.parseBoolean(" false 	", true));
    }

    @Test
    public final void testParseInt() {
        String _str = null;
        Assert.assertEquals(-1, stringUtils.parseInt(_str, -1));
        Assert.assertEquals(-1, stringUtils.parseInt(null, -1));
        Assert.assertEquals(-1, stringUtils.parseInt("-1", -1));
        Assert.assertEquals(-1, stringUtils.parseInt(" 	-1 ", -1));
        Assert.assertEquals(-1, stringUtils.parseInt("a", -1));
        Assert.assertEquals(-1, stringUtils.parseInt("a1", -1));
        Assert.assertEquals(-1, stringUtils.parseInt("True", -1));
        Assert.assertEquals(0, stringUtils.parseInt("0", -1));
        Assert.assertEquals(1, stringUtils.parseInt("1", -1));
        Assert.assertEquals(1, stringUtils.parseInt(" 1", -1));
        Assert.assertEquals(1, stringUtils.parseInt("1 ", -1));
        Assert.assertEquals(1, stringUtils.parseInt(" 1 ", -1));
        Assert.assertEquals(1, stringUtils.parseInt(" 	1 	", -1));
    }

    @Test
    public final void testParseLong() {
        String _str = null;
        Assert.assertEquals(-1, stringUtils.parseLong(_str, -1));
        Assert.assertEquals(-1, stringUtils.parseLong(null, -1));
        Assert.assertEquals(-1, stringUtils.parseLong("-1", -1));
        Assert.assertEquals(-1, stringUtils.parseLong(" 	-1 ", -1));
        Assert.assertEquals(-1, stringUtils.parseLong("a", -1));
        Assert.assertEquals(-1, stringUtils.parseLong("a1", -1));
        Assert.assertEquals(-1, stringUtils.parseLong("True", -1));
        Assert.assertEquals(0, stringUtils.parseLong("0", -1));
        Assert.assertEquals(1, stringUtils.parseLong("1", -1));
        Assert.assertEquals(1, stringUtils.parseLong(" 1", -1));
        Assert.assertEquals(1, stringUtils.parseLong("1 ", -1));
        Assert.assertEquals(1, stringUtils.parseLong(" 1 ", -1));
        Assert.assertEquals(1, stringUtils.parseLong(" 	1 	", -1));
    }

    @Test
    public final void testParseFloat() {
        String _str = null;
        Assert.assertEquals(-1f, stringUtils.parseFloat(_str, -1f), 0.00);
        Assert.assertEquals(-1f, stringUtils.parseFloat(null, -1f), 0.00);
        Assert.assertEquals(-1f, stringUtils.parseFloat("-1", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseFloat(" 	-1 ", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseFloat("a", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseFloat("a1", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseFloat("True", -1f), 0.00);
        Assert.assertEquals(0, stringUtils.parseFloat("0", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseFloat("1", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseFloat(" 1", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseFloat("1 ", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseFloat(" 1 ", -1), 0.00);
        Assert.assertEquals(0, stringUtils.parseFloat(".0", -1), 0.00);
        Assert.assertEquals(0.0f, stringUtils.parseFloat(".0", -1), 0.00);
        Assert.assertEquals(0.00f, stringUtils.parseFloat(".00", -1), 0.00);
        Assert.assertEquals(0f, stringUtils.parseFloat("0.0", -1), 0.00);
        Assert.assertEquals(3.14f, stringUtils.parseFloat("3.14", -1), 0.00);
        Assert.assertEquals(3.14f, stringUtils.parseFloat("3.14f", -1), 0.00);

        Assert.assertNotEquals(3.14, stringUtils.parseFloat("3.14f", -1), 0.00);
    }

    @Test
    public final void testParseDouble() {
        String _str = null;
        Assert.assertEquals(-1f, stringUtils.parseDouble(_str, -1f), 0.00);
        Assert.assertEquals(-1f, stringUtils.parseDouble(null, -1f), 0.00);
        Assert.assertEquals(-1f, stringUtils.parseDouble("-1", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseDouble(" 	-1 ", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseDouble("a", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseDouble("a1", -1f), 0.00);
        Assert.assertEquals(-1, stringUtils.parseDouble("True", -1f), 0.00);
        Assert.assertEquals(0, stringUtils.parseDouble("0", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseDouble("1", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseDouble(" 1", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseDouble("1 ", -1), 0.00);
        Assert.assertEquals(1, stringUtils.parseDouble(" 1 ", -1), 0.00);
        Assert.assertEquals(0, stringUtils.parseDouble(".0", -1), 0.00);
        Assert.assertEquals(0.0f, stringUtils.parseDouble(".0", -1), 0.00);
        Assert.assertEquals(0.00f, stringUtils.parseDouble(".00", -1), 0.00);
        Assert.assertEquals(0f, stringUtils.parseDouble("0.0", -1), 0.00);
        Assert.assertEquals(3.14, stringUtils.parseDouble("3.14", -1), 0.00);
        Assert.assertEquals(3.14, stringUtils.parseDouble("3.14f", -1), 0.00);
        Assert.assertEquals(3.14, stringUtils.parseDouble("3.14d", -1), 0.00);

        Assert.assertNotEquals(3.14, stringUtils.parseDouble("3.14a", -1), 0.00);
    }

    @Test
    public final void testParseBigDecimal() {
        String _str = null;
        BigDecimal _bd_1 = new BigDecimal(-1);
        Assert.assertEquals(new BigDecimal(-1), stringUtils.parseBigDecimal(_str, _bd_1));
        Assert.assertEquals(new BigDecimal(-1), stringUtils.parseBigDecimal(null, _bd_1));
        Assert.assertEquals(_bd_1, stringUtils.parseBigDecimal(_str, _bd_1));

        Assert.assertEquals(new BigDecimal("3.14"), stringUtils.parseBigDecimal(" 3.14", _bd_1));
        Assert.assertEquals(new BigDecimal("3.14"), stringUtils.parseBigDecimal("3.14 ", _bd_1));
        Assert.assertEquals(new BigDecimal("3.14"), stringUtils.parseBigDecimal(" 3.14	 ", _bd_1));
        Assert.assertNotEquals(new BigDecimal(3.14), stringUtils.parseBigDecimal("3.14", _bd_1));
        Assert.assertNotEquals(new BigDecimal(3.14f), stringUtils.parseBigDecimal("3.14f", _bd_1));
        Assert.assertNotEquals(new BigDecimal(3.14d), stringUtils.parseBigDecimal("3.14d", _bd_1));

        Assert.assertEquals(new BigDecimal(0), stringUtils.parseBigDecimal(" 0 ", _bd_1));
        Assert.assertEquals(new BigDecimal(1), stringUtils.parseBigDecimal(" 1	", _bd_1));
        Assert.assertEquals(new BigDecimal(315), stringUtils.parseBigDecimal("315", _bd_1));
    }

    @Test
    public final void testStartsWithIgnoreCase() {
        String _str = null;
        Assert.assertFalse(stringUtils.startsWithIgnoreCase(_str, "prefix_"));
        Assert.assertFalse(stringUtils.startsWithIgnoreCase(null, "prefix_"));

        Assert.assertTrue(stringUtils.startsWithIgnoreCase("prefix_str1_suffix", "prefix_"));
        Assert.assertTrue(stringUtils.startsWithIgnoreCase("1p_str1_suffix", "1p"));

        Assert.assertFalse(stringUtils.startsWithIgnoreCase(" prefix_str1_suffix", "prefix_"));
        Assert.assertFalse(stringUtils.startsWithIgnoreCase(" 	prefix_str1_suffix", "prefix_"));
        Assert.assertFalse(stringUtils.startsWithIgnoreCase("pr_str1_suffix", "prefix_"));
    }

    @Test
    public final void testEndsWithIgnoreCase() {
        String _str = null;
        Assert.assertFalse(stringUtils.endsWithIgnoreCase(_str, "_suffix"));
        Assert.assertFalse(stringUtils.endsWithIgnoreCase(null, "_suffix"));

        Assert.assertTrue(stringUtils.endsWithIgnoreCase("prefix_str1_suffix", "_suffix"));
        Assert.assertTrue(stringUtils.endsWithIgnoreCase("prefix_str1_s1", "s1"));
        Assert.assertTrue(stringUtils.endsWithIgnoreCase("prefix_str1_s1", "1"));

        Assert.assertFalse(stringUtils.endsWithIgnoreCase("prefix_str1_suffix ", "_suffix"));
        Assert.assertFalse(stringUtils.endsWithIgnoreCase("prefix_str1_s1", "s"));
    }

    @Test
    public final void testStartsWithChar() {
        String _str = null;
        Assert.assertNull(stringUtils.startsWithChar(_str, '/'));
        Assert.assertNull(stringUtils.startsWithChar(null, '/'));

        Assert.assertEquals("/api/test", stringUtils.startsWithChar("api/test", '/'));
        Assert.assertEquals("/api/test", stringUtils.startsWithChar("/api/test", '/'));
        Assert.assertEquals("/", stringUtils.startsWithChar("/", '/'));
        Assert.assertEquals("/", stringUtils.startsWithChar("", '/'));
    }

    @Test
    public final void testEndsWithChar() {
        String _str = null;
        Assert.assertNull(stringUtils.endsWithChar(_str, ','));
        Assert.assertNull(stringUtils.endsWithChar(null, ','));

        Assert.assertEquals("/a,/b,", stringUtils.endsWithChar("/a,/b", ','));
        Assert.assertEquals("/a,/b,", stringUtils.endsWithChar("/a,/b,", ','));
        Assert.assertEquals(",", stringUtils.endsWithChar(",", ','));
        Assert.assertEquals(",", stringUtils.endsWithChar("", ','));
    }

    @Test
    public final void testStartsWithoutChar() {
        String _str = null;
        Assert.assertNull(stringUtils.startsWithoutChar(_str, '/'));
        Assert.assertNull(stringUtils.startsWithoutChar(null, '/'));

        Assert.assertEquals("api/test", stringUtils.startsWithoutChar("api/test", '/'));
        Assert.assertEquals("api/test", stringUtils.startsWithoutChar("/api/test", '/'));
        Assert.assertEquals("", stringUtils.startsWithoutChar("/", '/'));
        Assert.assertEquals("", stringUtils.startsWithoutChar("", '/'));
    }

    @Test
    public final void testEndsWithoutChar() {
        String _str = null;
        Assert.assertNull(stringUtils.endsWithoutChar(_str, ','));
        Assert.assertNull(stringUtils.endsWithoutChar(null, ','));

        Assert.assertEquals("/a,/b", stringUtils.endsWithoutChar("/a,/b", ','));
        Assert.assertEquals("/a,/b", stringUtils.endsWithoutChar("/a,/b,", ','));
        Assert.assertEquals("", stringUtils.endsWithoutChar(",", ','));
        Assert.assertEquals("", stringUtils.endsWithoutChar("", ','));
    }

    @Test
    public final void testCapitalize() {
        String _str = null;
        Assert.assertEquals(null, stringUtils.capitalize(_str));
        Assert.assertEquals(_str, stringUtils.capitalize(null));

        Assert.assertEquals("First", stringUtils.capitalize("first"));
        Assert.assertEquals("First", stringUtils.capitalize("First"));
        Assert.assertEquals("FIrst", stringUtils.capitalize("fIrst"));
        Assert.assertEquals("FIrst", stringUtils.capitalize("FIrst"));

        Assert.assertNotEquals("First", stringUtils.capitalize(" first"));
    }

    @Test
    public final void testUnCapitalize() {
        String _str = null;
        Assert.assertEquals(null, stringUtils.unCapitalize(_str));
        Assert.assertEquals(_str, stringUtils.unCapitalize(null));

        Assert.assertEquals("first", stringUtils.unCapitalize("first"));
        Assert.assertEquals("first", stringUtils.unCapitalize("First"));
        Assert.assertEquals("fIrst", stringUtils.unCapitalize("fIrst"));
        Assert.assertEquals("fIrst", stringUtils.unCapitalize("FIrst"));

        Assert.assertNotEquals("first", stringUtils.unCapitalize(" First"));
    }

    @Test
    public final void testUnderscore() {
        Assert.assertNotNull(stringUtils.underscore(null));
        Assert.assertEquals("test", stringUtils.underscore("test"));
        Assert.assertEquals("test", stringUtils.underscore("Test"));
        Assert.assertEquals("t", stringUtils.underscore("t"));
        Assert.assertNotEquals("_t", stringUtils.underscore("T"));
        Assert.assertEquals("test_string", stringUtils.underscore("TestString"));
        Assert.assertEquals("test_a_b_c", stringUtils.underscore("TestABC"));
        Assert.assertEquals("i_test", stringUtils.underscore("ITest"));
    }

    @Test
    public final void testUpperCase() {
        String _str = null;
        Assert.assertEquals(_str, stringUtils.upperCase(null));
        Assert.assertEquals(null, stringUtils.upperCase(_str));
        Assert.assertEquals("TEST", stringUtils.upperCase("test"));
        Assert.assertNotEquals("TEST", stringUtils.upperCase(" test"));
    }

    @Test
    public final void testLowerCase() {
        String _str = null;
        stringUtils.lowerCase(_str);
        Assert.assertEquals(_str, stringUtils.lowerCase(null));
        Assert.assertEquals(null, stringUtils.lowerCase(_str));
        Assert.assertEquals("test", stringUtils.lowerCase("TEST"));
        Assert.assertNotEquals("test", stringUtils.lowerCase(" TEST "));
    }

    @Test
    public final void testReplace() {
        String _str = null;
        Assert.assertEquals(null, stringUtils.replace(_str, null, null));
        Assert.assertEquals(_str, stringUtils.replace(_str, "", ""));
        Assert.assertEquals(_str, stringUtils.replace(_str, "a", "b"));

        _str = " Hello, This is test 	str.";
        Assert.assertEquals(" Hello, This is test 	str.", stringUtils.replace(_str, "", "-"));
        Assert.assertEquals("-Hello,-This-is-test-	str.", stringUtils.replace(_str, " ", "-"));
        Assert.assertEquals("Hello,Thisistest	str.", stringUtils.replace(_str, " ", ""));
        Assert.assertEquals(" Hello, Thxx xx test 	str.", stringUtils.replace(_str, "is", "xx"));
        Assert.assertEquals(" Hello, Th  test 	str.", stringUtils.replace(_str, "is", ""));
        Assert.assertEquals(" Hello, This is ?es? 	s?r.", stringUtils.replace(_str, "t", "?"));
        Assert.assertNotEquals(" Hello, ?his is ?es? 	s?r.", stringUtils.replace(_str, "t", "?"));
    }

    @Test
    public final void testDelete() {
        String _str = null;
        Assert.assertEquals(null, stringUtils.delete(_str, null));
        Assert.assertEquals(_str, stringUtils.delete(_str, ""));
        Assert.assertEquals(_str, stringUtils.delete(_str, "x"));

        _str = " Hello, This is test 	str.";
        Assert.assertEquals(" Hello, This is test 	str.", stringUtils.delete(_str, null));
        Assert.assertEquals(" Hello, This is test 	str.", stringUtils.delete(_str, ""));
        Assert.assertEquals("Hello,Thisistest	str.", stringUtils.delete(_str, " "));
        Assert.assertEquals(" Hello, This is test str.", stringUtils.delete(_str, "	"));
        Assert.assertEquals(" Hello, Th  test 	str.", stringUtils.delete(_str, "is"));
        Assert.assertEquals(" Hello, This is es 	sr.", stringUtils.delete(_str, "t"));
        Assert.assertNotEquals(" Hello, his is es 	sr.", stringUtils.delete(_str, "t"));
    }

    @Test
    public final void testDeleteAny() {
        String _str = null;
        Assert.assertEquals(null, stringUtils.deleteAny(_str, null));
        Assert.assertEquals(_str, stringUtils.deleteAny(_str, ""));
        Assert.assertEquals(null, stringUtils.deleteAny(_str, "x"));

        _str = " Hello, This is test 	str.";
        Assert.assertEquals(" Hello, This is test 	str.", stringUtils.deleteAny(_str, null));
        Assert.assertEquals(" Hello, This is test 	str.", stringUtils.deleteAny(_str, ""));
        Assert.assertEquals(" Hello, Thi i e 	r.", stringUtils.deleteAny(_str, "sbt"));
        Assert.assertEquals(" Hello, Th  tet 	tr.", stringUtils.deleteAny(_str, "is"));
        Assert.assertEquals(" Hllo, Thi i tt 	tr.", stringUtils.deleteAny(_str, "es"));
    }
}
