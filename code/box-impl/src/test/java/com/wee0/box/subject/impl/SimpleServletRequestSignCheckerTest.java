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

package com.wee0.box.subject.impl;

import com.wee0.box.util.shortcut.ByteUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.wee0.box.BoxConstants.UTF8;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/2/23 9:12
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleServletRequestSignCheckerTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String _key = "aW8Kd5Qe56db11eIa29002D2aG12010w";
        String _data = "hello";
        Mac _mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec _secretKeySpec = new SecretKeySpec(_key.getBytes(), _mac.getAlgorithm());
        _mac.init(_secretKeySpec);
        byte[] _bytes = _mac.doFinal(_data.getBytes(UTF8));
        String _hex = ByteUtils.bytesToHexString(_bytes);
        System.out.println("hex:" + _hex);
        System.out.println("FBF0551A6FC5AC89FD80EEE4E73A38F04A0E8DCB28C161698FB17D73F080A2E4".equals(_hex));
        System.out.println("fbf0551a6fc5ac89fd80eee4e73a38f04a0e8dcb28c161698fb17d73f080a2e4".equals(_hex.toLowerCase()));

    }

}
