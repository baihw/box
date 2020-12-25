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

import com.wee0.box.subject.ITokenHelper;
import com.wee0.box.util.shortcut.ByteUtils;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.JsonUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ObjectStreamException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/16 7:11
 * @Description 一个简单的令牌操作助手实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleTokenHelper implements ITokenHelper {

    // 默认密钥
    private String secret;
    // 默认算法名称
    private String algorithm;

    @Override
    public String encode(Map<String, Object> data, String secret, String algorithm) {
        if (null == data || data.isEmpty())
            throw new IllegalArgumentException("data cannot be empty!");
        secret = CheckUtils.checkNotTrimEmpty(secret, "secret cannot be empty!");
        algorithm = CheckUtils.checkTrimEmpty(algorithm, DEF_ALGORITHM);
        String _dataString = JsonUtils.writeToString(data);
//        System.out.println("_dataString:" + _dataString);
        Key _secretKey = new SecretKeySpec(secret.getBytes(), algorithm);
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, _secretKey);
            byte[] _finalData = cipher.doFinal(_dataString.getBytes());
            return ByteUtils.bytesToHexString(_finalData);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> decode(String token, String secret, String algorithm) {
        token = CheckUtils.checkNotTrimEmpty(token, "token cannot be empty!");
        secret = CheckUtils.checkNotTrimEmpty(secret, "secret cannot be empty!");
        algorithm = CheckUtils.checkTrimEmpty(algorithm, DEF_ALGORITHM);
        Key _secretKey = new SecretKeySpec(secret.getBytes(), algorithm);
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, _secretKey);
            byte[] _finalData = cipher.doFinal(ByteUtils.hexStringToBytes(token));
            String _tokenDataString = new String(_finalData);
            System.out.println("_tokenDataString:" + _tokenDataString);
            return JsonUtils.readToMap(_tokenDataString);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleTokenHelper() {
        if (null != SimpleTokenHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleTokenHelperHolder {
        private static final SimpleTokenHelper _INSTANCE = new SimpleTokenHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleTokenHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleTokenHelper me() {
        return SimpleTokenHelperHolder._INSTANCE;
    }
}
