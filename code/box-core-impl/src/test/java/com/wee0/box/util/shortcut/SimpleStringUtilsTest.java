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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @ArgumentsSource(ParseNumberTest.class)
    public void testParseInt(int expected, Object val, int defVal) {
        Assertions.assertEquals(expected, _impl.parseInt(val, defVal));
    }

    public static class ParseNumberTest implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(-1, (String) null, -1), // null strings should be considered blank
                    Arguments.of(-1, null, -1),
                    Arguments.of(-1, "-1", -1),
                    Arguments.of(-1, " 	-1 ", -1),
                    Arguments.of(-1, "a", -1),
                    Arguments.of(-1, "a1", -1),
                    Arguments.of(-1, "True", -1),
                    Arguments.of(0, "0", -1),
                    Arguments.of(1, "1", -1),
                    Arguments.of(1, " 1", -1),
                    Arguments.of(1, "1 ", -1),
                    Arguments.of(1, " 1 ", -1),
                    Arguments.of(1, " 	1 	", -1)
            );
        }
    }

}
