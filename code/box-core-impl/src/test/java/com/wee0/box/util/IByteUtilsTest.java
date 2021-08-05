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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 22:16
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Disabled("for inheritance")
public class IByteUtilsTest {

    // 实现类实例
    protected static IByteUtils impl;

    static final String _S1 = "Hello, World!";
    static final String _S1Hex = "48656C6C6F2C20576F726C6421";

    @Test
    public void bytesToHexString() {
//        Assert.assertEquals(DatatypeConverter.printHexBinary(_S1.getBytes()), impl.bytesToHexString(_S1.getBytes()));
        Assertions.assertEquals(_S1Hex, impl.bytesToHexString(_S1.getBytes()));
    }

    @Test
    public void hexStringToBytes() {
        Assertions.assertEquals(_S1, new String(impl.hexStringToBytes(_S1Hex)));
    }

}
