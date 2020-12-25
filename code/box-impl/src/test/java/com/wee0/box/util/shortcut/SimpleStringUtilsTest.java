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

package com.wee0.box.util.shortcut;

import com.wee0.box.util.IStringUtils;
import com.wee0.box.util.impl.SimpleStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/16 7:12
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/

public class SimpleStringUtilsTest {

    static final IStringUtils _impl = SimpleStringUtils.me();

    @RunWith(Parameterized.class)
    public static class ParseNumberTest {
        private int _expected;
        private Object _given;
        private int _def;

        public ParseNumberTest(int expected, Object given, int def) {
            this._expected = expected;
            this._given = given;
            this._def = def;
        }

        @Parameterized.Parameters()
        public static Iterable<Object[]> data() {
            return Arrays.asList(
                    new Object[]{-1, (String) null, -1},
                    new Object[]{-1, null, -1},
                    new Object[]{-1, "-1", -1},
                    new Object[]{-1, " 	-1 ", -1},
                    new Object[]{-1, "a", -1},
                    new Object[]{-1, "a1", -1},
                    new Object[]{-1, "True", -1},
                    new Object[]{0, "0", -1},
                    new Object[]{1, "1", -1},
                    new Object[]{1, " 1", -1},
                    new Object[]{1, "1 ", -1},
                    new Object[]{1, " 1 ", -1},
                    new Object[]{1, " 	1 	", -1}
            );
        }


        @Test
        public void testParseInt() {
            Assert.assertEquals(this._expected, _impl.parseInt(this._given, this._def));
        }

    }

}
